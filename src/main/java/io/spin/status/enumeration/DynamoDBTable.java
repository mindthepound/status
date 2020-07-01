package io.spin.status.enumeration;

public enum DynamoDBTable {
    ADMIN("spin.status.admin"),
    STATUS_CHANGE("spin.status.change"),
    SYNC_USER("spin.sync.user"),
    SYNC_ALARM_USER("spin.alarm.user"),
    SPIN_STATUS_CONFIG("spin.status.config"),
    STATUS_LOG("spin.status.log"),
    STATUS_LOG_REDIS("spin.status.log.redis"),
    /* DA server 를 위한 Dynamo DB 를 생성해 본다. */
//    MGS_SB("da.msg.sb"),
//    MGS_SEND("da.msg.send"),
    /* 새롭게 만드는 status Server Schema. */
    STATUS_SERVER_STATUS_CHANGE("status.server.status.change"),
    STATUS_SERVER_SYNC_USER("status.server.sync.user"),
    STATUS_SERVER_SYNC_ALARM_USER("status.server.alarm.user"),
    ;

    final private String tableName;

    DynamoDBTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

}
