package io.spin.status.service.status.change.impl;

import io.spin.status.domain.dynamoDB.status.server.StatusServerAlarmUser;
import io.spin.status.domain.dynamoDB.status.server.StatusServerStatusChange;
import io.spin.status.domain.dynamoDB.status.server.StatusServerSyncUser;
import io.spin.status.domain.dynamoDB.status.status.*;
import io.spin.status.dto.Result;
import io.spin.status.dto.status.change.StatusChangeDTO;
import io.spin.status.enumeration.ResultCode;
import io.spin.status.repository.dynamoDB.status.change.StatusChangeDynamoDBRepository;
import io.spin.status.service.status.change.StatusChangeService;
import io.spin.status.util.DateUtil;
import io.spin.status.util.FCMUtil;
import io.spin.status.util.JSONUtil;
import io.spin.status.util.SessionUtil;
import io.spin.status.util.redis.impl.RedisMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class StatusChangeServiceImplement implements StatusChangeService {

    @Value("${spin.point.available-point-month}")
    private Integer avaliablePointMonth;

    @Value("${spin.date.miliseconds.1day}")
    private Integer onedayMiliseconds;

    private RedisMessagePublisher redisMessagePublisher;

    private StatusChangeDynamoDBRepository statusChangeDynamoDBRepository;

    private DateUtil dateUtil;
    private SessionUtil sessionUtil;
    private FCMUtil fcmUtil;
    private JSONUtil jsonUtil;

    private boolean isExcelBatchProgress = false;
    private int total = 0;
    private int progress = 0;
    private List<StatusChangeDTO> statusChangeDTOS;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public StatusChangeServiceImplement(
            RedisMessagePublisher redisMessagePublisher,
            StatusChangeDynamoDBRepository statusChangeDynamoDBRepository,
            DateUtil dateUtil,
            SessionUtil sessionUtil,
            FCMUtil fcmUtil,
            JSONUtil jsonUtil
    ) {
        this.redisMessagePublisher = redisMessagePublisher;
        this.statusChangeDynamoDBRepository = statusChangeDynamoDBRepository;

        this.dateUtil = dateUtil;
        this.sessionUtil = sessionUtil;
        this.fcmUtil = fcmUtil;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public Result saveStatusChange(StatusServerStatusChange statusServerStatusChange) {
        statusServerStatusChange.setCreated(System.currentTimeMillis());
        log.info(String.valueOf(System.currentTimeMillis()));
        LocalDateTime now = LocalDateTime.now();

        statusServerStatusChange.setDate(now.format(dateformatter));
        statusServerStatusChange.setCreatedAt(now.format(formatter));
        //statusChange.setDate(dateUtil.today());
        statusChangeDynamoDBRepository.saveStatusChange(statusServerStatusChange);
        return new Result(ResultCode.OK);
    }
    @Override
    public Result saveAddUser(StatusServerSyncUser statusServerSyncUser) {
        LocalDateTime now = LocalDateTime.now();
        statusServerSyncUser.setDate(now.format(dateformatter));
        statusChangeDynamoDBRepository.saveAddUser(statusServerSyncUser);
        return new Result(ResultCode.OK);
    }

    @Override
    public Result saveAddPhone(StatusServerAlarmUser statusServerAlarmUser) {
        LocalDateTime now = LocalDateTime.now();
        statusServerAlarmUser.setDate(now.format(dateformatter));
        statusChangeDynamoDBRepository.saveAddPhone(statusServerAlarmUser);
        return new Result(ResultCode.OK);
    }

    @Override
    public Result saveInsertMsgLog(MsgSb msgSb) {
        statusChangeDynamoDBRepository.saveMsgsb(msgSb);
        return new Result(ResultCode.OK);
    }

    @Override
    public Result saveInsertMsgsendLog(MsgSend msgSend) {
        statusChangeDynamoDBRepository.saveMsgsend(msgSend);
        return new Result(ResultCode.OK);
    }

    @Override
    public Result deleteStatusChange(StatusServerStatusChange statusChange) {
        statusChange.setCreated(System.currentTimeMillis() - onedayMiliseconds);
        statusChange.setDate(dateUtil.yesterday());
        statusChangeDynamoDBRepository.deleteStatusChangeByLatestItem(statusChange);
        return new Result(ResultCode.OK);
    }


}
