package io.spin.status.repository.dynamoDB.status;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.spin.status.domain.dynamoDB.status.Point;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.util.aws.DynamoDBHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PointDynamoDBRepository {

    private DynamoDBMapper dynamoDBMapper;
    private DynamoDBHelper dynamoDBHelper;

    @Autowired
    public PointDynamoDBRepository(
            DynamoDBMapper dynamoDBMapper,
            DynamoDBHelper dynamoDBHelper
    ) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDBHelper = dynamoDBHelper;
    }

    public QueryResultPage<Point> getPointByOwner(DynamoDBSearchParam dynamoDBSearchParam) {
        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withKeyConditionExpression("#owner = :owner")
                .withExpressionAttributeNames(new NameMap()
                        .with("#owner", "owner"))
                .withExpressionAttributeValues(new ValueMap()
                        .with(":owner", new AttributeValue().withS(dynamoDBSearchParam.getOwner())))
                .withScanIndexForward(false);
        return dynamoDBMapper.queryPage(Point.class, dynamoDBQueryExpression, dynamoDBHelper.getTestEnvironmentConfig());
    }

    public QueryResultPage<Point> getPointByOwnerWithMonth(String owner, String date) {
        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withKeyConditionExpression("#owner = :owner and #date > :date")
                .withExpressionAttributeNames(new NameMap()
                        .with("#owner", "owner")
                        .with("#date", "date"))
                .withExpressionAttributeValues(new ValueMap()
                        .with(":owner", new AttributeValue().withS(owner))
                        .with(":date", new AttributeValue().withS(date)))
                .withScanIndexForward(true);
        return dynamoDBMapper.queryPage(Point.class, dynamoDBQueryExpression);
    }

    public void savePoint(Point point) {
        dynamoDBMapper.save(point, dynamoDBHelper.getTestEnvironmentConfig());
    }

    public void updatePoint(Point point) {
        dynamoDBMapper.save(point, dynamoDBHelper.getUpdateEnvironmentConfig());
    }
}
