package io.spin.status.repository.dynamoDB.token;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.spin.status.domain.dynamoDB.token.Token;
import io.spin.status.util.aws.DynamoDBHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class TokenDynamoDBRepository {

    private DynamoDBMapper dynamoDBMapper;
    private DynamoDBHelper dynamoDBHelper;

    @Autowired
    public TokenDynamoDBRepository(
            DynamoDBMapper dynamoDBMapper,
            DynamoDBHelper dynamoDBHelper
    ) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDBHelper = dynamoDBHelper;
    }

    public String getTokenByOwner(String owner) {

        DynamoDBQueryExpression dynamoDBQueryExpression = new DynamoDBQueryExpression()
                .withKeyConditionExpression("#owner = :owner")
                .withIndexName("owner-index")
                .withConsistentRead(false)
                .withExpressionAttributeNames(new NameMap()
                        .with("#owner", "owner"))
                .withExpressionAttributeValues(new ValueMap()
                        .with(":owner", new AttributeValue().withS(owner)));

        QueryResultPage<Token> queryResultPage = dynamoDBMapper.queryPage(Token.class, dynamoDBQueryExpression, dynamoDBHelper.getTestEnvironmentConfig());

        if (queryResultPage.getCount() > 0) return queryResultPage.getResults().get(0).getToken();
        else return null;
    }
}
