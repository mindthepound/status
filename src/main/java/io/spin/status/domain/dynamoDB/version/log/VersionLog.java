package io.spin.status.domain.dynamoDB.version.log;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.version.log")
public class VersionLog implements Serializable {

    private static final long serialVersionUID = 8339972976107975694L;

    @DynamoDBHashKey(attributeName = "os")
    private String os;
    @DynamoDBRangeKey(attributeName = "date")
    private String date;
    @DynamoDBAttribute(attributeName = "cashId")
    private String cashId;
    @DynamoDBAttribute(attributeName = "version")
    private String version;
    @DynamoDBAttribute(attributeName = "url")
    private String url;

}
