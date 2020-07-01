package io.spin.status.service.tran.impl;

import io.spin.status.dto.Result;
import io.spin.status.dto.emma.TranDTO;
import io.spin.status.enumeration.ResultCode;
import io.spin.status.service.tran.TranService;
import io.spin.status.sync.db.mapper.SyncDBMapper;
import io.spin.status.util.DateUtil;
import io.spin.status.util.JSONUtil;
import io.spin.status.util.redis.impl.RedisMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class TranServiceImplement implements TranService {

    private RedisMessagePublisher redisMessagePublisher;
    private DateUtil dateUtil;
    private JSONUtil jsonUtil;
    private SyncDBMapper syncDBMapper;

    @Autowired
    public TranServiceImplement(
            RedisMessagePublisher redisMessagePublisher,
            DateUtil dateUtil,
            JSONUtil jsonUtil,
            SyncDBMapper syncDBMapper
    ) {
        this.redisMessagePublisher = redisMessagePublisher;
        this.dateUtil = dateUtil;
        this.jsonUtil = jsonUtil;
        this.syncDBMapper = syncDBMapper;
    }

    @Override
    public Result saveTranMessage(TranDTO tranDTO) throws Exception {
        syncDBMapper.insertTran(tranDTO);
        return new Result(ResultCode.OK);
    }
}
