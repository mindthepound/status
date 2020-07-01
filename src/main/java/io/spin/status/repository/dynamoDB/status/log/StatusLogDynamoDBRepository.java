package io.spin.status.repository.dynamoDB.status.log;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.spin.status.domain.dynamoDB.status.log.PointLog;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.enumeration.DynamoDBTable;
import io.spin.status.util.JSONUtil;
import io.spin.status.util.aws.DynamoDBHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Slf4j
@Repository
public class StatusLogDynamoDBRepository {

    private DynamoDBHelper dynamoDBHelper;
    private DynamoDBMapper dynamoDBMapper;
    private DynamoDB dynamoDB;
    private Table table;
    private JSONUtil jsonUtil;

    @Autowired
    public StatusLogDynamoDBRepository(
            AmazonDynamoDB amazonDynamoDB,
            DynamoDBHelper dynamoDBHelper,
            DynamoDBMapper dynamoDBMapper,
            JSONUtil jsonUtil
    ) {
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        this.table = dynamoDB.getTable(DynamoDBTable.STATUS_LOG.getTableName());
        this.dynamoDBHelper = dynamoDBHelper;
        this.dynamoDBMapper = dynamoDBMapper;
        this.jsonUtil = jsonUtil;
    }

    public QueryResultPage<PointLog> getPointLogByOwner(
            DynamoDBSearchParam dynamoDBSearchParam
    ) {
        try {
            DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                    .withIndexName("owner-created-index")
                    .withScanIndexForward(false)
                    .withLimit(dynamoDBSearchParam.getLimit())
                    .withProjectionExpression("#owner, #typeId, #created, #point, #type, #appId, #device, #appName")
                    .withKeyConditionExpression("#owner = :owner")
                    .withExpressionAttributeNames(new NameMap()
                            .with("#owner", "owner")
                            .with("#typeId", "typeId")
                            .with("#created", "created")
                            .with("#point", "point")
                            .with("#type", "type")
                            .with("#appId", "appId")
                            .with("#device", "device")
                            .with("#appName", "appName"))
                    .withExpressionAttributeValues(new ValueMap()
                            .with(":owner", new AttributeValue().withS(dynamoDBSearchParam.getOwner())));

            if (dynamoDBSearchParam.getStartKey() != null)
                dynamoDBQueryExpression.withExclusiveStartKey(
                        jsonUtil.jsonToDynamoDBPageMap(dynamoDBSearchParam.getStartKey())
                );

            return dynamoDBMapper.queryPage(PointLog.class, dynamoDBQueryExpression, dynamoDBHelper.getTestEnvironmentConfig());
        } catch (DynamoDBMappingException e) {
            checkPointLogWrongStored(
                    dynamoDBSearchParam.getOwner()
            );
            return getPointLogByOwner(dynamoDBSearchParam);
        }
    }

    public void updatePointLog(
            PointLog pointLog
    ) {
        dynamoDBMapper.save(pointLog, dynamoDBHelper.getUpdateEnvironmentConfig());
    }

    private void checkPointLogWrongStored(String owner) {
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression("#owner = :owner")
                .withNameMap(new NameMap()
                    .with("#owner", "owner"))
                .withValueMap(new ValueMap()
                    .with(":owner", owner))
                .withConsistentRead(true);

        ItemCollection<QueryOutcome> items = table.query(querySpec);

        Iterator<Item> iterator = items.iterator();

        while (iterator.hasNext()) {

            Item item = iterator.next();

            if (item.getJSON("point").indexOf("\"") != -1) {

                updatePointLog(new PointLog(
                        item.getString("owner"),
                        item.getString("typeId"),
                        item.getString("created"),
                        Integer.parseInt(item.getString("point")),
                        item.getString("type"),
                        item.getString("appId"),
                        item.getString("device"),
                        item.getString("appName")
                ));
            }
        }
    }
}
