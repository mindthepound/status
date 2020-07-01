package io.spin.status.repository.dynamoDB.status.log.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.spin.status.domain.dynamoDB.status.log.redis.StatusLogRedis;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.util.aws.DynamoDBHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class StatusLogRedisDynamoDBRepository {

    private DynamoDBHelper dynamoDBHelper;
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public StatusLogRedisDynamoDBRepository(
            DynamoDBHelper dynamoDBHelper,
            DynamoDBMapper dynamoDBMapper
    ) {
        this.dynamoDBHelper = dynamoDBHelper;
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public QueryResultPage<StatusLogRedis> getPointLogRedisByType(
            DynamoDBSearchParam dynamoDBSearchParam
    ) {
        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withKeyConditionExpression("#type = :type and #date between :start and :end")
                .withConsistentRead(false)
                .withExpressionAttributeNames(new NameMap()
                        .with("#type", "type")
                        .with("#date", "date"))
                .withExpressionAttributeValues(new ValueMap()
                        .with(":type", new AttributeValue().withS(dynamoDBSearchParam.getType()))
                        .with(":start", new AttributeValue().withS(dynamoDBSearchParam.getStart()))
                        .with(":end", new AttributeValue().withS(dynamoDBSearchParam.getEnd())));

        return dynamoDBMapper.queryPage(
                StatusLogRedis.class,
                dynamoDBQueryExpression,
                dynamoDBHelper.getTestEnvironmentConfig()
        );
    }

    public QueryResultPage<StatusLogRedis> getPointLogRedisByKind(
            DynamoDBSearchParam dynamoDBSearchParam
    ) {
        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withKeyConditionExpression("#kind = :kind and #date between :start and :end")
                .withIndexName("kind-date-index")
                .withConsistentRead(false)
                .withExpressionAttributeNames(new NameMap()
                        .with("#kind", "kind")
                        .with("#date", "date"))
                .withExpressionAttributeValues(new ValueMap()
                        .with(":kind", new AttributeValue().withS(dynamoDBSearchParam.getType()))
                        .with(":start", new AttributeValue().withS(dynamoDBSearchParam.getStart()))
                        .with(":end", new AttributeValue().withS(dynamoDBSearchParam.getEnd())));

        return dynamoDBMapper.queryPage(
                StatusLogRedis.class,
                dynamoDBQueryExpression,
                dynamoDBHelper.getTestEnvironmentConfig()
        );
    }

    public void saveLog(StatusLogRedis statusLogRedis) {
        dynamoDBMapper.save(statusLogRedis, dynamoDBHelper.getTestEnvironmentConfig());
    }
}
