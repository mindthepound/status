package io.spin.status.repository.redis.planinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CoinboxRedisRepository {

    private RedisTemplate redisTemplate;

    private String KEY = "planInfo:status:hash";

    @Autowired
    public CoinboxRedisRepository(
            RedisTemplate redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    public void delete() {
        redisTemplate.delete(KEY);
    }
}
