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
@DynamoDBTable(tableName = "status.server.sync.user")
public class  StatusServerSyncUser implements Serializable {

    private static final long serialVersionUID = 4702182380371174188L;

    @NotNull
    @DynamoDBHashKey(attributeName = "type")
    private String type;
    @DynamoDBRangeKey(attributeName = "id")
    @DynamoDBIndexRangeKey(attributeName = "id", localSecondaryIndexName = "type-id")
    private String id;

    @DynamoDBIndexRangeKey(attributeName = "useYn", localSecondaryIndexName = "type-useYn-index")
    private String useYn;



    @DynamoDBAttribute(attributeName = "comment")
    private String comment;
    @DynamoDBAttribute(attributeName = "date")
    private String date;

}
