package io.spin.status.domain.dynamoDB.status.server;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "status.server.alarm.user")
public class StatusServerAlarmUser implements Serializable {

    private static final long serialVersionUID = 4702182380371174188L;

    @NotNull
    @DynamoDBHashKey(attributeName = "type")
    private String type;
    @DynamoDBRangeKey(attributeName = "phone")
    @DynamoDBIndexRangeKey(attributeName = "phone", localSecondaryIndexName = "type-phone")
    private String phone;
    @DynamoDBIndexRangeKey(attributeName = "useYn", localSecondaryIndexName = "type-useYn-index")
    private String useYn;

    @DynamoDBAttribute(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "date")
    private String date;
}
