package io.spin.status.domain.dynamoDB.user;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.user.cs")
public class UserCS implements Serializable {

    private static final long serialVersionUID = 1318193207242187195L;

    @DynamoDBHashKey(attributeName = "owner")
    private String owner;
    @DynamoDBRangeKey(attributeName = "stamp")
    private Long stamp;
    @DynamoDBAttribute(attributeName = "date")
    private String date;
    @DynamoDBAttribute(attributeName = "cashId")
    private String cashId;
    @DynamoDBAttribute(attributeName = "field")
    private String field;
    @DynamoDBAttribute(attributeName = "before")
    private String before;
    @DynamoDBAttribute(attributeName = "after")
    private String after;
}
