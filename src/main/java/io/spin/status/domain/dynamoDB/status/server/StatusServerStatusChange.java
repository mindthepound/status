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
@DynamoDBTable(tableName = "status.server.status.change")
public class StatusServerStatusChange implements Serializable {

    private static final long serialVersionUID = 4702182380371174188L;

    @NotNull
    @DynamoDBHashKey(attributeName = "date")
    private String date;
    @DynamoDBRangeKey(attributeName = "created")
    @DynamoDBIndexRangeKey(attributeName = "created", localSecondaryIndexName = "date-created")
    private long created;

    @DynamoDBIndexRangeKey(attributeName = "owner", localSecondaryIndexName = "date-owner-index")
    private String owner;

    @DynamoDBAttribute(attributeName = "type")
    private String type;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute(attributeName = "comment")
    private String comment;

}
