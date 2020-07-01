package io.spin.status.schedule.reports;

import io.spin.status.domain.dynamoDB.status.server.StatusServerStatusChange;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.dto.emma.TranDTO;
import io.spin.status.repository.dynamoDB.status.change.StatusChangeDynamoDBRepository;
import io.spin.status.repository.dynamoDB.status.log.redis.StatusLogRedisDynamoDBRepository;
import io.spin.status.service.status.change.StatusChangeService;
import io.spin.status.service.tran.TranService;
import io.spin.status.sync.db.mapper.SyncDBMapper;
import io.spin.status.util.DateUtil;
import io.spin.status.util.aws.SESHelper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
@Controller
@Service
@RequestMapping("/reports/status/delete")
public class DeleteStatusTaskURLmapping {

    private StatusChangeDynamoDBRepository statusChangeDynamoDBRepository;
    private StatusLogRedisDynamoDBRepository statusLogRedisDynamoDBRepository;
    private DynamoDBSearchParam dynamoDBSearchParam;
    private StatusChangeService statusChangeService;
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

    @Autowired
    public DeleteStatusTaskURLmapping(
            StatusChangeDynamoDBRepository statusChangeDynamoDBRepository,
            StatusLogRedisDynamoDBRepository statusLogRedisDynamoDBRepository,
            StatusChangeService statusChangeService,
            SESHelper sesHelper,
            TranService tranService,
            SyncDBMapper syncDBMapper,
            DateUtil dateUtil
    ) {
        this.statusChangeDynamoDBRepository = statusChangeDynamoDBRepository;
        this.statusLogRedisDynamoDBRepository = statusLogRedisDynamoDBRepository;
        this.statusChangeService = statusChangeService;
        this.sesHelper = sesHelper;
        this.dateUtil = dateUtil;
        this.tranService = tranService;
        this.syncMapper = syncDBMapper;

    }

    /**
     * DynamoDBTable 에서 spin.status.change 로그를 가져와 이상이 있으면 문자를 발송하는 Job 을 실행한다.
     * 1. dynamoDB 에서 spin.status.change 로그를 가져온다.
     *
     */

    //@Environment(only = "production")
    //@Scheduled(cron = "0 30 8 * * ?")
    //@Scheduled(cron = "0 0 * * * ?"))

    @ApiOperation(value = "reports 의 status 값 전날 이전 것은 삭제", notes = "reports/status/delete")
    @PostMapping("")
    public void deleteStatusChange(@RequestBody StatusServerStatusChange statusServerStatusChange) throws Exception {

        log.info("---- start deleteStatusChange Schedule ----");

        statusChangeService.deleteStatusChange(statusServerStatusChange);

        log.info("currentTimeMillis() : " + System.currentTimeMillis());
        log.info("---- end deleteStatusChange Schedule ----");
    }
}
