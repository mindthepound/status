package io.spin.status.schema.dynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import io.spin.status.enumeration.DynamoDBTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StatusServerAlarmUser {

    @Autowired
    public StatusServerAlarmUser(AmazonDynamoDB amazonDynamoDB) {

        createTable(new DynamoDB(amazonDynamoDB));
    }

    private void createTable(DynamoDB dynamoDB) {

        List<AttributeDefinition> attributeDefinitionList = new ArrayList<>();
        attributeDefinitionList.add(
                new AttributeDefinition()
                        .withAttributeName("type")
                        .withAttributeType(ScalarAttributeType.S)
        );
        attributeDefinitionList.add(
                new AttributeDefinition()
                        .withAttributeName("phone")
                        .withAttributeType(ScalarAttributeType.S)
        );
        attributeDefinitionList.add(
                new AttributeDefinition()
                        .withAttributeName("useYn")
                        .withAttributeType(ScalarAttributeType.S)
        );

        List<KeySchemaElement> tableKeySchema = new ArrayList<>();
        tableKeySchema.add(
                new KeySchemaElement()
                        .withAttributeName("type")
                        .withKeyType(KeyType.HASH)
        );
        tableKeySchema.add(
                new KeySchemaElement()
                        .withAttributeName("phone")
                        .withKeyType(KeyType.RANGE)
        );

        List<KeySchemaElement> indexKeySchema = new ArrayList<>();
        indexKeySchema.add(
                new KeySchemaElement()
                        .withAttributeName("type")
                        .withKeyType(KeyType.HASH)
        );
        indexKeySchema.add(
                new KeySchemaElement()
                        .withAttributeName("useYn")
                        .withKeyType(KeyType.RANGE)
        );

        LocalSecondaryIndex localSecondaryIndex = new LocalSecondaryIndex()
                .withIndexName("type-useYn-index")
                .withKeySchema(indexKeySchema)
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(DynamoDBTable.STATUS_SERVER_SYNC_ALARM_USER.getTableName())
                .withKeySchema(tableKeySchema)
                .withLocalSecondaryIndexes(localSecondaryIndex)
                .withAttributeDefinitions(attributeDefinitionList)
                .withProvisionedThroughput(
                        new ProvisionedThroughput()
                                .withReadCapacityUnits(1L)
                                .withWriteCapacityUnits(1L)
                );

        try {

            dynamoDB.createTable(createTableRequest);

        } catch (ResourceInUseException e) {

        }
    }
}
