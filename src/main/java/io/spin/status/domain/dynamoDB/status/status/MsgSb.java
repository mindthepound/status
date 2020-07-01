package io.spin.status.domain.dynamoDB.status.status;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "da.msg.sb")
public class MsgSb implements Serializable {

    private static final long serialVersionUID = 4702182380371174188L;

    @NotNull
    @DynamoDBHashKey(attributeName = "bsid")
    private String bsid;
    @DynamoDBRangeKey(attributeName = "date")
    @DynamoDBIndexRangeKey(attributeName = "date", localSecondaryIndexName = "date-index")
    private String date;

    @DynamoDBIndexRangeKey(attributeName = "sn", localSecondaryIndexName = "sn-index")
    private String sn;

    @DynamoDBAttribute(attributeName = "recp")
    private String recp;

    @DynamoDBAttribute(attributeName = "regDt")
    private String regDt;

}
