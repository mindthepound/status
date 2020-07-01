package io.spin.status.domain.dynamoDB.status.log.redis;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.point.log.redis")
public class StatusLogRedis implements Serializable {

    private static final long serialVersionUID = 4479209243502787915L;

    @DynamoDBHashKey(attributeName = "type")
    private String type;
    @DynamoDBRangeKey(attributeName = "date")
    @DynamoDBIndexRangeKey(attributeName = "date", globalSecondaryIndexName = "kind-date-index")
    private String date;
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "point")
    private Long point;

}
