package io.spin.status.domain.dynamoDB.status;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import io.spin.status.util.aws.CustomNumberDynamoDBTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.point")
public class Point implements Serializable {

    private static final long serialVersionUID = 2383029376638542678L;

    @DynamoDBHashKey(attributeName = "owner")
    private String owner;
    @DynamoDBRangeKey(attributeName = "date")
    private String date;
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    @DynamoDBTypeConverted(converter = CustomNumberDynamoDBTypeConverter.class)
    @DynamoDBAttribute(attributeName = "total")
    private Integer total;
    @DynamoDBAttribute(attributeName = "check")
    private Map<String, List<Integer>> check;

    public Point(Point point) {
        this.owner = point.owner;
        this.date = point.date;
        this.total = point.total;
        this.check = point.check;
    }
}
