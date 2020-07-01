package io.spin.status.repository.dynamoDB.status.change;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.spin.status.domain.dynamoDB.status.server.StatusServerAlarmUser;
import io.spin.status.domain.dynamoDB.status.server.StatusServerStatusChange;
import io.spin.status.domain.dynamoDB.status.server.StatusServerSyncUser;
import io.spin.status.domain.dynamoDB.status.status.MsgSb;
import io.spin.status.domain.dynamoDB.status.status.MsgSend;
import io.spin.status.enumeration.DynamoDBTable;
import io.spin.status.enumeration.ServiceType;
import io.spin.status.util.JSONUtil;
import io.spin.status.util.aws.DynamoDBHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class StatusChangeDynamoDBRepository {

    @Value("${spring.profiles.active}")
    private String profile;

    private DynamoDB dynamoDB;
    private Table table;
    private Index index;
    private DynamoDBMapper dynamoDBMapper;
    private DynamoDBHelper dynamoDBHelper;
    private JSONUtil jsonUtil;

    @Autowired
    public StatusChangeDynamoDBRepository(
            AmazonDynamoDB amazonDynamoDB,
            DynamoDBMapper dynamoDBMapper,
            DynamoDBHelper dynamoDBHelper,
            JSONUtil jsonUtil
    ) {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        this.table = dynamoDB.getTable(DynamoDBTable.STATUS_CHANGE.getTableName());
        this.index = table.getIndex("date-index");
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDBHelper = dynamoDBHelper;
        this.jsonUtil = jsonUtil;
    }

    /**
     * 날짜 & last row 값으로 데이터 조회
     */
    public QueryResultPage<StatusServerStatusChange> getStatusChangeByLatestItem(
            String date, long createdInterval, String type, String owner,
            Map<String, AttributeValue> lastEvaluatedKey
    ) {
        Map<String, AttributeValue> map = new HashMap<>();
        log.info(String.valueOf(System.currentTimeMillis()));
        log.info(String.valueOf(createdInterval));
        map.put(":date", new AttributeValue().withS(date));
        map.put(":owner", new AttributeValue().withS(owner));
        map.put(":type", new AttributeValue().withS(type));
        map.put(":created", new AttributeValue().withN(String.valueOf(createdInterval)));

        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
//                .withIndexName("date-owner-index")
//                .withKeyConditionExpression("#date = :date and #owner = :owner")
//                .withFilterExpression("#type = :type")
//                .withExpressionAttributeNames(new NameMap()
//                        .with("#date", "date")
//                        .with("#owner", "owner")
//                        .with("#type", "type"))

                .withKeyConditionExpression("#date = :date and #created > :created")
                .withFilterExpression("#type = :type and #owner = :owner")
                .withExpressionAttributeNames(new NameMap()
                        .with("#date", "date")
                        .with("#owner", "owner")
                        .with("#created", "created")
                        .with("#type", "type"))
                .withExpressionAttributeValues(map)
                //.withLimit(1)
                .withScanIndexForward(false)
                //.withScanIndexForward(true)
                .withConsistentRead(false);

        if (lastEvaluatedKey != null) {
            dynamoDBQueryExpression.withExclusiveStartKey(lastEvaluatedKey);
        }

        // return dynamoDBMapper.queryPage(StatusChange.class, dynamoDBQueryExpression, dynamoDBHelper.getTestEnvironmentConfig());
        return dynamoDBMapper.queryPage(StatusServerStatusChange.class, dynamoDBQueryExpression);
    }

    /**
     * 날짜 값으로 데이터 삭제
     */
    public void deleteStatusChangeByLatestItem(
            StatusServerStatusChange statusChange
    ) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":date", new AttributeValue().withS(statusChange.getDate()));
        map.put(":created", new AttributeValue().withS(Long.toString(statusChange.getCreated())));
        map.put(":owner", new AttributeValue().withS(statusChange.getOwner()));


        DynamoDBDeleteExpression dynamoDBDeleteQueryExpression = new DynamoDBDeleteExpression()
                .withConditionExpression("#owner = :owner AND #date <= :date AND #created <= :created")
//                .withConditionalOperator("#date <= :date")
//                .withConditionalOperator("#createdAt <= :createdAt")
                .withExpressionAttributeNames(new NameMap()
                        .with("#date", "date")
                        .with("#owner", "owner")
                        .with("#created", "created"))
                .withExpressionAttributeValues(map);
        dynamoDBMapper.delete(statusChange, dynamoDBDeleteQueryExpression);
    }

    /**
     * SyncUser 데이터 조회
     */
    public QueryResultPage<StatusServerSyncUser> getSyncUserItem(
            ServiceType type,
            String useYn,
            Map<String, AttributeValue> lastEvaluatedKey
    ) {
        /* 이것도 추후에 status 만들고 query 로 변경하쟈. */
//        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
//                .withProjectionExpression("#type")
//                .withExpressionAttributeNames(new NameMap()
//                        .with("#type", "type"));
//
//        if (lastEvaluatedKey != null) {
//            dynamoDBScanExpression.withExclusiveStartKey(lastEvaluatedKey);
//        }

        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":type", new AttributeValue().withS(String.valueOf(type)));
        map.put(":useYn", new AttributeValue().withS(String.valueOf(useYn)));

        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withIndexName("type-useYn-index")
                .withKeyConditionExpression("#type = :type and #useYn = :useYn")
                //.withLimit(150)
                //.withFilterExpression("#bsid = :bsid and #gcode = :gcode")
                .withExpressionAttributeNames(new NameMap()
                        .with("#type", "type")
                        .with("#useYn", "useYn"))
                .withExpressionAttributeValues(map)
                .withConsistentRead(false);

        if (lastEvaluatedKey != null) {
            dynamoDBQueryExpression.withExclusiveStartKey(lastEvaluatedKey);
        }

        // return dynamoDBMapper.queryPage(StatusChange.class, dynamoDBQueryExpression, dynamoDBHelper.getTestEnvironmentConfig());
        return dynamoDBMapper.queryPage(StatusServerSyncUser.class, dynamoDBQueryExpression);
    }

    /**
     * SyncAlarm 을 받을 User 데이터 조회
     */
    public QueryResultPage<StatusServerAlarmUser> getAlarmUserItem(
            ServiceType type,
            String id,
            String useYn,
            Map<String, AttributeValue> lastEvaluatedKey
    ) {

        Map<String, AttributeValue> map = new HashMap<>();
        map.put(":type", new AttributeValue().withS(String.valueOf(type)));
        map.put(":id", new AttributeValue().withS(String.valueOf(id)));
        map.put(":useYn", new AttributeValue().withS(String.valueOf(useYn)));

//        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
////                .withProjectionExpression("#owner")
////                .withExpressionAttributeNames(new NameMap()
////                        .with("#owner", "owner"))
//                .withLimit(limit);

        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withIndexName("type-useYn-index")
                .withKeyConditionExpression("#type = :type and #useYn = :useYn")
                //.withLimit(150)
                .withFilterExpression("#id = :id")
                .withExpressionAttributeNames(new NameMap()
                        .with("#type", "type")
                        .with("#id", "id")
                        .with("#useYn", "useYn"))
                .withExpressionAttributeValues(map)
                .withConsistentRead(false);

        if (lastEvaluatedKey != null) {
            dynamoDBQueryExpression.withExclusiveStartKey(lastEvaluatedKey);
        }

        // return dynamoDBMapper.queryPage(StatusChange.class, dynamoDBQueryExpression, dynamoDBHelper.getTestEnvironmentConfig());
        return dynamoDBMapper.queryPage(StatusServerAlarmUser.class, dynamoDBQueryExpression);
    }

    public ScanResultPage<StatusServerStatusChange> getListData(
            Integer limit,
            Map<String, AttributeValue> lastEvaluatedKey
    ) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withProjectionExpression("#owner")
                .withExpressionAttributeNames(new NameMap()
                        .with("#owner", "owner"))
                .withLimit(limit);

        if (lastEvaluatedKey != null) {
            dynamoDBScanExpression.withExclusiveStartKey(lastEvaluatedKey);
        }
        return dynamoDBMapper.scanPage(StatusServerStatusChange.class, dynamoDBScanExpression, dynamoDBHelper.getTestEnvironmentConfig());
    }

    /**
     * Status Log 기록
     */
    public void saveStatusChange(
            StatusServerStatusChange statusServerStatusChange
    ) {
        // dynamoDBMapper.save(statusChange, dynamoDBHelper.getTestEnvironmentConfig());
        dynamoDBMapper.save(statusServerStatusChange);
    }

    /**
     * Add user (Sync User 등록)
     */
    public void saveAddUser(
            StatusServerSyncUser statusServerSyncUser
    ) {
        // dynamoDBMapper.save(statusChange, dynamoDBHelper.getTestEnvironmentConfig());
        dynamoDBMapper.save(statusServerSyncUser);
    }

    /**
     * Add user (Add Phone  등록)
     */
    public void saveAddPhone(
            StatusServerAlarmUser statusServerAlarmUser
    ) {
        // dynamoDBMapper.save(statusChange, dynamoDBHelper.getTestEnvironmentConfig());
        dynamoDBMapper.save(statusServerAlarmUser);
    }

    /**
     * da.msg.sb DA Server 의 save Test.
     */
    public void saveMsgsb(
            MsgSb msgSb
    ) {
        // dynamoDBMapper.save(statusChange, dynamoDBHelper.getTestEnvironmentConfig());
        dynamoDBMapper.save(msgSb);
    }

    /**
     * da.msg.send DA Server 의 save Test.
     */
    public void saveMsgsend(
            MsgSend msgSend
    ) {
        dynamoDBMapper.save(msgSend);
    }


    public void deleteStatusChange(
            StatusServerStatusChange statusServerStatusChange)
    {
        dynamoDBMapper.delete(statusServerStatusChange);
    }
}
