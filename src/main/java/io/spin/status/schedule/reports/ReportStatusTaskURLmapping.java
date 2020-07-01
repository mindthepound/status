package io.spin.status.schedule.reports;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.spin.status.domain.dynamoDB.status.server.StatusServerAlarmUser;
import io.spin.status.domain.dynamoDB.status.server.StatusServerStatusChange;
import io.spin.status.domain.dynamoDB.status.server.StatusServerSyncUser;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.dto.emma.TranDTO;
import io.spin.status.enumeration.ServiceType;
import io.spin.status.repository.dynamoDB.status.change.StatusChangeDynamoDBRepository;
import io.spin.status.repository.dynamoDB.status.log.redis.StatusLogRedisDynamoDBRepository;
import io.spin.status.service.tran.TranService;
import io.spin.status.sync.db.mapper.SyncDBMapper;
import io.spin.status.util.DateUtil;
import io.spin.status.util.aws.SESHelper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@Service
@RequestMapping("/reports/status")
public class ReportStatusTaskURLmapping {

    private StatusChangeDynamoDBRepository statusChangeDynamoDBRepository;
    private StatusLogRedisDynamoDBRepository statusLogRedisDynamoDBRepository;
    private DynamoDBSearchParam dynamoDBSearchParam;
    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String,Object> valueOperations;
    private TranService tranService;
    private SyncDBMapper syncMapper;
    private TranDTO tranDTO;

    private SESHelper sesHelper;
    private DateUtil dateUtil;
    private Date date;
    private Timestamp timestamp;

    @Value("${spin.email}")
    private String receiveEmailAddress;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Value("${spin.date.miliseconds.1day}")
    private Long dayInterval;

    @Value("${spin.date.miliseconds.3minutes}")
    private Long minutesInterval;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public ReportStatusTaskURLmapping(
            StatusChangeDynamoDBRepository statusChangeDynamoDBRepository,
            StatusLogRedisDynamoDBRepository statusLogRedisDynamoDBRepository,
            RedisTemplate redisTemplate,
            SESHelper sesHelper,
            TranService tranService,
            SyncDBMapper syncDBMapper,
            DateUtil dateUtil
    ) {
        this.statusChangeDynamoDBRepository = statusChangeDynamoDBRepository;
        this.statusLogRedisDynamoDBRepository = statusLogRedisDynamoDBRepository;
        this.redisTemplate = redisTemplate;
        this.sesHelper = sesHelper;
        this.dateUtil = dateUtil;
        this.tranService = tranService;
        this.syncMapper = syncDBMapper;
        valueOperations = redisTemplate.opsForValue();

    }

    /**
     * DynamoDBTable 에서 status.server.status.change 로그를 가져와 이상이 있으면 문자를 발송하는 Job 을 실행한다.
     * 1. dynamoDB 에서 status.server.status.change 로그를 가져온다.
     *
     */

    //@Environment(only = "production")
    //@Scheduled(cron = "0 30 8 * * ?")
    //@Scheduled(cron = "0 0 * * * ?"))

    @ApiOperation(value = "reports 의 status 확인", notes = "reports/status")
    @GetMapping("")
    public void reportStatusChange() throws Exception {

        for (ServiceType type : ServiceType.values()) {

            log.info("---- URL mapping reportStatusChange Schedule type ----");
            log.info("ServiceType: " + type);
            log.info(String.valueOf("URL mapping currentMillisecondns : " + System.currentTimeMillis()));

            LocalDateTime now = LocalDateTime.now();
            log.info((now.format(dateFormatter)));

            // Redis 에서 먼저 값을 읽는 시도를 한다. 없으면 Dynamo DB 에서 조회.
            Object syncUserRedisObjectList = redisTemplate.opsForValue().get("status:"+type+":userlist");
            ObjectMapper objectMapper = new ObjectMapper();
            List<StatusServerSyncUser> syncUserList = null;
            syncUserList = objectMapper.convertValue(syncUserRedisObjectList, new TypeReference<List<StatusServerSyncUser>>() {
            }); // SyncUser 에 Redis 에서 불러온 LinkedHashMap 을 매핑한다.

            if (syncUserList == null || syncUserList.size() == 0) {
                // DynamoDB query. 이걸 TYPE 별 쿼리를 해온다.
                syncUserList = getSyncUser(type);
                // alram List Redis Set
                redisTemplate.opsForValue().set("status:"+type+":userlist", syncUserList, 600, TimeUnit.SECONDS);
                //valueOperations.set("sync:userlist", syncUserList, 86400, TimeUnit.SECONDS); //  1 day expire.
            }

            log.info(String.valueOf("userList" + syncUserList));

            /* Sync 고객으로 추가된 customer ID 를 scan 으로 가지고 온다. */
            /* TODO: DynamoDB 에서 scan 으로 가져오기 때문에 추후에 Redis 로 옮긴다. */
            /* forEach 함 돌려야 함. */
            for (StatusServerSyncUser list : syncUserList) {
                log.info(String.valueOf(list));
                log.info(String.valueOf(list.getType()));
                log.info(String.valueOf(list.getId()));


                /* todo: type 과 시용자 ID 를 가지고 마지막으로 찍힌 로그를 읽어온다. */
                List<StatusServerStatusChange> reportStatus = getReportStatus(now.format(dateFormatter), (System.currentTimeMillis() - minutesInterval), list.getType(), list.getId());
                /* 현재 시간과 비교 currentTimeMillis() */
                // System.currentTimeMillis();
                log.info(list.getType()+":"+list.getId()+""+"reportStatusLogSize :"+reportStatus.size());
                try {
                    if (reportStatus.size() == 0 || (System.currentTimeMillis() - reportStatus.get(0).getCreated()) > minutesInterval) { // 일단 3분이상 차이가 나면 alarm 을 띄운다.
                        // 일단 3분 (180000 miliseconds 이상 request 의 값의 interval 이 생기면 alarm 을 띄운다.)

                        if (reportStatus.size() > 0) {
                            log.info(String.valueOf("created :" + reportStatus.get(0).getCreated()));
                            log.info(String.valueOf("currentMillisecondns : " + System.currentTimeMillis()));
                            log.info(String.valueOf("minus result : " + (System.currentTimeMillis() - reportStatus.get(0).getCreated())));
                        } else {
                            log.info("size == 0");
                        }
                        /* 에러메세지를 전송할 전화번호를 불러온다. */
                        // Redis 에서 먼저 값을 읽는 시도를 한다. 없으면 Dynamo DB 에서 조회.
                        /* todo : Redis Setting 도 "status:"+type+"+":list.getId()":alarmlist" 로 바뀌어야 함. 일단 주석. */
                        /* Alarm List 는 일단 디비에서 가지고 오쟈.
                        Object syncAlarmRedisObjectList = redisTemplate.opsForValue().get("status:"+type+":alarmlist");
                        // objectMapper = new ObjectMapper();
                        List<StatusServerAlarmUser> syncAlarmList = null;
                        syncAlarmList = objectMapper.convertValue(syncAlarmRedisObjectList, new TypeReference<List<StatusServerAlarmUser>>() {
                        }); // SyncUser 에 Redis 에서 불러온 LinkedHashMap 을 매핑한다.
                        log.info(String.valueOf("status:"+type+":alarmlist:" + syncAlarmList));
                         */
                        List<StatusServerAlarmUser> syncAlarmList = null;

                        if (syncAlarmList == null || syncAlarmList.size() == 0) {
                            /* todo: Alarm 을 보내고자 하는 사용자 리스트를 불러온다. 이또한 type 과 id 그리고 useYn 이 Y 인 사용자들에게만 해당하여 쿼리한다. */
                            syncAlarmList = getAlarmUser(type, list.getId());
                            // alram List Redis Set
                            // valueOperations.set("sync:alarmlist", syncAlarmList, 86400, TimeUnit.SECONDS); //  1 day expire.
                            // redisTemplate.opsForValue().set("status:"+type+":alarmlist", syncAlarmList, 600, TimeUnit.SECONDS);
                            // log.info(String.valueOf("status:"+type+":"+list.getId()+":alarmlist" + syncAlarmList));
                        }

                        log.info("syncAlarmList : "+String.valueOf(syncAlarmList));
                        /* forEach 함 돌려야 함. */
                        for (StatusServerAlarmUser alarmList : syncAlarmList) {
                            log.info("SendMessage To AlarmList"+String.valueOf(alarmList));
                            //log.info("AlarmListPhone"+String.valueOf(alarmList.getPhone()));

                            /* RDS 에 보낼 문자 메세지 저장. */
                            TranDTO tranDTO = new TranDTO();
                            tranDTO.setDateClientReq(LocalDateTime.now());

                            //tranDTO.setContent(" biztalk Common Status Server error Test");

                            tranDTO.setContent(list.getComment()); // User 정보에 있는 comment 에 있는 값으로 문자를 보낸다.

                            tranDTO.setCallback("1688-3764");
                            tranDTO.setServiceType('0');
                            tranDTO.setBroadcastYn('N');
                            tranDTO.setMsgStatus('1');
                            tranDTO.setRecipientNum(alarmList.getPhone());
                            tranDTO.setId(0); // 필요없는 값 같은데.. 어차피 Emma 에서는 mt_pr 을 auto increment 함.
                            tranService.saveTranMessage(tranDTO);
                        }

                    }
                } catch (IndexOutOfBoundsException error) {
                    // Output expected IndexOutOfBoundsExceptions.
                    log.info("IndexOutOfBoundsException");
                } catch (Exception | Error exception) {
                    // Output unexpected Exceptions/Errors.
                    log.info("Error Exception");
                }

                try {
                    log.info(String.valueOf(System.currentTimeMillis() - reportStatus.get(0).getCreated()));
                } catch (IndexOutOfBoundsException error) {
                    // Output expected IndexOutOfBoundsExceptions.
                    log.info("IndexOutOfBoundsException");
                } catch (Exception | Error exception) {
                    // Output unexpected Exceptions/Errors.
                    log.info("Error Exception");
                }
            }

            log.info("currentTimeMillis() : " + System.currentTimeMillis());
            log.info("---- end currentTimeMillis Schedule ----");
        }
    }

    private List<StatusServerStatusChange> getReportStatus(String date, @NotNull long createdInterval, String type, String id) {

        List<StatusServerStatusChange> statusChangeList = new ArrayList<>();
        QueryResultPage queryResultPage = null;

            queryResultPage = statusChangeDynamoDBRepository.getStatusChangeByLatestItem(
                    date, createdInterval, type, id,
                    queryResultPage == null ? null : queryResultPage.getLastEvaluatedKey()
            );
        log.info(String.valueOf(System.currentTimeMillis()));
        statusChangeList.addAll(queryResultPage.getResults());

        return statusChangeList;
    }

    private List<StatusServerSyncUser> getSyncUser(ServiceType type) {

        List<StatusServerSyncUser> syncUserList = new ArrayList<>();

        //ScanResultPage scanResultPage = null;
        QueryResultPage queryResultPage = null;

        /* User (client 고객) 는 "Y" 값으로 사용하는 사용자만 Alarm 을 받는 조건으로 들어간다.
           "N" 으로 등록한 사용자는 리스트 조회에서 빠지므로 alarm 을 체크하는 용도로 들어가지 않는다. */
        do {
            queryResultPage = statusChangeDynamoDBRepository.getSyncUserItem(
                    type,
                    "Y",    // 추후에 Y, N 값 constant 로 바꿈.
                    queryResultPage == null ? null : queryResultPage.getLastEvaluatedKey()
            );

            syncUserList.addAll(queryResultPage.getResults());

        } while (queryResultPage.getLastEvaluatedKey() != null);

        return syncUserList;
    }

    private List<StatusServerAlarmUser> getAlarmUser(ServiceType type, String id) {

        List<StatusServerAlarmUser> syncAlarmList = new ArrayList<>();

        // ScanResultPage scanResultPage = null;
        QueryResultPage queryResultPage = null;

        do {
            queryResultPage = statusChangeDynamoDBRepository.getAlarmUserItem(
                    type,
                    id,
                    "Y",
                    queryResultPage == null ? null : queryResultPage.getLastEvaluatedKey()
            );

            syncAlarmList.addAll(queryResultPage.getResults());

        } while (queryResultPage.getLastEvaluatedKey() != null);

        return syncAlarmList;
    }

}
