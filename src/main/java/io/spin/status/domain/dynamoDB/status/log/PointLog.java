package io.spin.status.domain.dynamoDB.status.log;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.status.log")
public class PointLog implements Serializable {

    private static final long serialVersionUID = 897523953607248686L;

    @DynamoDBHashKey(attributeName = "owner")
    @DynamoDBIndexHashKey(attributeName = "owner", globalSecondaryIndexName = "owner-created-index")
    private String owner;
    @DynamoDBRangeKey(attributeName = "typeId")
    private String typeId;
    @DynamoDBAttribute(attributeName = "created")
    @DynamoDBIndexRangeKey(attributeName = "created", globalSecondaryIndexName = "owner-created-index")
    private String created;
    @DynamoDBAttribute(attributeName = "point")
    private Integer point;
    @DynamoDBAttribute(attributeName = "type")
    private String type;
    @DynamoDBAttribute(attributeName = "appId")
    private String appId;
    @DynamoDBAttribute(attributeName = "device")
    private String device;
    @DynamoDBAttribute(attributeName = "appName")
    private String appName;

}
