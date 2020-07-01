package io.spin.status.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DynamoDBQueryPageResult<T> extends DynamoDBSearchParam{

    private QueryResultPage<T> queryResult;

    public DynamoDBQueryPageResult(
            DynamoDBSearchParam dynamoDBSearchParam
    ) {
        this.limit = dynamoDBSearchParam.getLimit();
        this.startKey = dynamoDBSearchParam.getStartKey();
        this.code = dynamoDBSearchParam.getCode();
        this.start = dynamoDBSearchParam.getStart();
        this.end = dynamoDBSearchParam.getEnd();
        this.owner = dynamoDBSearchParam.getOwner();
        this.partner = dynamoDBSearchParam.getPartner();
        this.from = dynamoDBSearchParam.getFrom();
        this.to = dynamoDBSearchParam.getTo();
        this.osType = dynamoDBSearchParam.getOsType();
    }
}
