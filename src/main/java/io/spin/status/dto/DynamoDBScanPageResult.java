package io.spin.status.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DynamoDBScanPageResult<T> extends DynamoDBSearchParam {

    private ScanResultPage<T> pageResult;

    public DynamoDBScanPageResult(
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
    }

}
