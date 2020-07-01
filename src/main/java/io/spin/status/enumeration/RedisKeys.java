package io.spin.status.enumeration;

public enum RedisKeys {

    /**
     * redis key setting
     */
    STATUS_CHANGE_LIST("status:change:list"),
    ;

    final private String redisKey;

    RedisKeys(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getRedisKey() {
        return redisKey;
    }
}
