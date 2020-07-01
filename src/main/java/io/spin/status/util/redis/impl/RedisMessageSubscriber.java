package io.spin.status.util.redis.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMessageSubscriber implements MessageListener {

    private RedisTemplate redisTemplate;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RedisMessageSubscriber(
            RedisTemplate redisTemplate,
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.redisTemplate = redisTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        simpMessagingTemplate.convertAndSend(
                "/pointBatch",
                new String(message.toString())
        );

        // TODO 개발이 완료 된 후 로그를 삭제 해야 함

        log.info(new String(message.toString()));

    }
}
