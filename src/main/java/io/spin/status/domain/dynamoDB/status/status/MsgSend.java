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
@DynamoDBTable(tableName = "da.msg.send")
public class MsgSend implements Serializable {

    private static final long serialVersionUID = 4702182380371174188L;

    @NotNull
    @DynamoDBHashKey(attributeName = "bsid")
    private String bsid;
    @DynamoDBRangeKey(attributeName = "sn")
    @DynamoDBIndexRangeKey(attributeName = "sn", localSecondaryIndexName = "bsid-sn")
    private String sn;

    @DynamoDBIndexRangeKey(attributeName = "date", localSecondaryIndexName = "bsid-date-index")
    private String date;

    @DynamoDBAttribute(attributeName = "msgidx")
    private String msgidx;

    @DynamoDBAttribute(attributeName = "msgc")
    private String msgc;

    @DynamoDBAttribute(attributeName = "cc")
    private String cc;

    @DynamoDBAttribute(attributeName = "recp")
    private String recp;

    @DynamoDBAttribute(attributeName = "req_server")
    private String req_server;

    @DynamoDBAttribute(attributeName = "cs")
    private String cs;

    @DynamoDBAttribute(attributeName = "da")
    private String da;

    @DynamoDBAttribute(attributeName = "ts")
    private String ts;

    @DynamoDBAttribute(attributeName = "ra")
    private String ra;

    @DynamoDBAttribute(attributeName = "res")
    private String res;

    @DynamoDBAttribute(attributeName = "regDt")
    private String regDt;

    @DynamoDBAttribute(attributeName = "sentDt")
    private String sentDt;

    @DynamoDBAttribute(attributeName = "recvDt")
    private String recvDt;

    @DynamoDBAttribute(attributeName = "resDt")
    private String resDt;

    @DynamoDBAttribute(attributeName = "sdk")
    private String sdk;

    @DynamoDBAttribute(attributeName = "tmplt")
    private String tmplt;

    @DynamoDBAttribute(attributeName = "resm")
    private String resm;

    @DynamoDBAttribute(attributeName = "tmo")
    private Integer tmo;
}
