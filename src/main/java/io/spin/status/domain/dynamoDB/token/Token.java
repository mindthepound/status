package io.spin.status.domain.dynamoDB.token;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.token")
public class Token implements Serializable {

    private static final long serialVersionUID = -476004756311991887L;

    @DynamoDBHashKey(attributeName = "token")
    private String token;
    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "owner-index", attributeName = "owner")
    private String owner;

}
