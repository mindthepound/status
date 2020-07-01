package io.spin.status.repository.dynamoDB.version;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.spin.status.domain.dynamoDB.version.log.VersionLog;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.util.JSONUtil;
import io.spin.status.util.aws.DynamoDBHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class VersionLogDynamoDBRepository {

    private DynamoDBMapper dynamoDBMapper;
    private DynamoDBHelper dynamoDBHelper;
    private JSONUtil jsonUtil;

    @Autowired
    public VersionLogDynamoDBRepository(
            DynamoDBMapper dynamoDBMapper,
            DynamoDBHelper dynamoDBHelper,
            JSONUtil jsonUtil
    ) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDBHelper = dynamoDBHelper;
        this.jsonUtil = jsonUtil;
    }

    public QueryResultPage<VersionLog> getVersionLogsByOs(DynamoDBSearchParam dynamoDBSearchParam) {
        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withKeyConditionExpression("#os = :os")
                .withScanIndexForward(false)
                .withLimit(dynamoDBSearchParam.getLimit())
                .withExpressionAttributeNames(new NameMap()
                        .with("#os", "os"))
                .withExpressionAttributeValues(new ValueMap()
                        .with(":os", new AttributeValue().withS(dynamoDBSearchParam.getOsType().name())));

        if (dynamoDBSearchParam.getStartKey() != null) {
            dynamoDBQueryExpression.withExclusiveStartKey(
                    jsonUtil.jsonToDynamoDBPageMap(dynamoDBSearchParam.getStartKey()));
        }
        return dynamoDBMapper.queryPage(VersionLog.class, dynamoDBQueryExpression, dynamoDBHelper.getTestEnvironmentConfig());
    }
    public void saveVersionLog(VersionLog versionLog) {
        dynamoDBMapper.save(versionLog, dynamoDBHelper.getTestEnvironmentConfig());
    }
}
