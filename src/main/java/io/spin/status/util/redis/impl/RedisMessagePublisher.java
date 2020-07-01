package io.spin.status.util.redis.impl;

import io.spin.status.util.redis.MessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisMessagePublisher implements MessagePublisher {

    private RedisTemplate redisTemplate;
    private ChannelTopic channelTopic;

    @Autowired
    public RedisMessagePublisher(
            RedisTemplate redisTemplate,
            ChannelTopic channelTopic
    ) {
        this.redisTemplate = redisTemplate;
        this.channelTopic = channelTopic;
    }

    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
