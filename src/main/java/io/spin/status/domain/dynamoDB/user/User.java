package io.spin.status.domain.dynamoDB.user;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import io.spin.status.util.aws.CustomNumberDynamoDBTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.user")
public class User implements Serializable {

    private static final long serialVersionUID = -3927502263501603704L;

    /**
     * HashKey
     */
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    /**
     * Global Secondary Index
     */
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "phone-index", attributeName = "phone")
    private String phone;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "kakao-index", attributeName = "kakao")
    private String kakao;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "fb-index", attributeName = "fb")
    private String facebook;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "uid-index", attributeName = "uid")
    private String uid;

    /**
     * Datas
     */
    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;
    @DynamoDBAttribute(attributeName = "abuseBanAt")
    private String abuseBanAt;
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    @DynamoDBAttribute(attributeName = "smsAuth")
    private Boolean smsAuth;
    @DynamoDBAttribute(attributeName = "type")
    private String type;
    @DynamoDBAttribute(attributeName = "pushId")
    private String pushId;
    @DynamoDBAttribute(attributeName = "device")
    private String device;
    @DynamoDBAttribute(attributeName = "watch")
    private String watch;
    @DynamoDBAttribute(attributeName = "lastAccess")
    private String lastAccess;
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    @DynamoDBTypeConverted(converter = CustomNumberDynamoDBTypeConverter.class)
    @DynamoDBAttribute(attributeName = "point")
    private Integer point;
    @DynamoDBAttribute(attributeName = "recommendPoint")
    private Integer recommendPoint;
    @DynamoDBAttribute(attributeName = "gameItemUpdated")
    private String gameItemUpdated;
    @DynamoDBAttribute(attributeName = "code")
    private String code;
    @DynamoDBAttribute(attributeName = "nickname")
    private String nickname;
    @DynamoDBAttribute(attributeName = "partners")
    private List<String> partners;
    @DynamoDBAttribute(attributeName = "profileUrl")
    private String profileUrl;
    @DynamoDBAttribute(attributeName = "birth")
    private String birth;
    @DynamoDBAttribute(attributeName = "gender")
    private String gender;
    @DynamoDBAttribute(attributeName = "height")
    private Integer height;
    @DynamoDBAttribute(attributeName = "weight")
    private Integer weight;
    @DynamoDBAttribute(attributeName = "friendCount")
    private Integer friendCount;
    @DynamoDBAttribute(attributeName = "badgeCount")
    private Integer badgeCount;
    @DynamoDBAttribute(attributeName = "requestCount")
    private Integer requestCount;
    @DynamoDBAttribute(attributeName = "countryCode")
    private String countryCode;
    @DynamoDBAttribute(attributeName = "playerId")
    private String playerId;
    @DynamoDBAttribute(attributeName = "isTest")
    private Integer isTest;
}
