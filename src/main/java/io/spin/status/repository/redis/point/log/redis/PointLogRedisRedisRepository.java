package io.spin.status.repository.redis.point.log.redis;

import io.spin.status.enumeration.NoticeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
public class PointLogRedisRedisRepository {

    private RedisTemplate redisTemplate;

    private String KEY = "cash:";

    @Autowired
    public PointLogRedisRedisRepository(
            RedisTemplate redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    public Map<String, Long> getAll(NoticeType type) {
        return redisTemplate.opsForHash().entries(generateKey(type));
    }

    public void delete(NoticeType type, String hashKey) {
        redisTemplate.opsForHash().delete(generateKey(type), hashKey);
    }

    private String generateKey(NoticeType type) { return KEY + type.name(); }
}
