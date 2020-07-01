package io.spin.status.domain.dynamoDB.admin;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "spin.status.admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = -4300739391501063944L;

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    @DynamoDBAttribute(attributeName = "pw")
    private String pw;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "roles")
    private String roles;
    @DynamoDBAttribute(attributeName = "lastPasswordChange")
    private String lastPasswordChange;
}
