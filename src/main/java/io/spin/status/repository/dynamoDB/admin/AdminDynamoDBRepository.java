package io.spin.status.repository.dynamoDB.admin;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import io.spin.status.domain.dynamoDB.admin.Admin;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.enumeration.DynamoDBTable;
import io.spin.status.util.aws.DynamoDBHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;

@Slf4j
@Repository
public class AdminDynamoDBRepository {

    private DynamoDBHelper dynamoDBHelper;
    private DynamoDBMapper dynamoDBMapper;
    private DynamoDB dynamoDB;
    private Table table;

    @Autowired
    public AdminDynamoDBRepository(
            AmazonDynamoDB amazonDynamoDB,
            DynamoDBHelper dynamoDBHelper,
            DynamoDBMapper dynamoDBMapper
    ) {
        this.dynamoDBHelper = dynamoDBHelper;
        this.dynamoDBMapper = dynamoDBMapper;
        this.dynamoDB = new DynamoDB(amazonDynamoDB);
        this.table = dynamoDB.getTable(DynamoDBTable.ADMIN.getTableName());
    }

    @RolesAllowed("ROLE_SUPER")
    public ScanResultPage<Admin> getAdmins(DynamoDBSearchParam dynamoDBSearchParam) {
        HashMap<String, AttributeValue> paginationKey = new HashMap<>();
        paginationKey.put("id", new AttributeValue(dynamoDBSearchParam.getStartKey()));

        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
        if (dynamoDBSearchParam.getStartKey() != null) {
            dynamoDBScanExpression = new DynamoDBScanExpression()
                    .withLimit(dynamoDBSearchParam.getLimit())
                    .withExclusiveStartKey(paginationKey);
        }

        return dynamoDBMapper.scanPage(Admin.class, dynamoDBScanExpression);
    }

    public Admin getAdmin(String id) {
        DynamoDBMapperConfig test1 = dynamoDBHelper.getConsistentConfig();
        DynamoDBMapperConfig test2 = dynamoDBHelper.getConsistentEnvironmentConfig();
        log.info(String.valueOf(test1));
        log.info(String.valueOf(test2));
        return dynamoDBMapper.load(
                Admin.class,
                id,
                //dynamoDBHelper.getConsistentConfig()
                dynamoDBHelper.getConsistentEnvironmentConfig()
        );
    }

    // public void createAdmin(Admin admin) { dynamoDBMapper.save(admin); }
    public void createAdmin(Admin admin) { dynamoDBMapper.save(admin,
                                           dynamoDBHelper.getConsistentEnvironmentConfig()
                                            );
                                         }

    public void updateAdmin(Admin admin) {
        dynamoDBMapper.save(admin, dynamoDBHelper.getUpdateConfig());
    }

    @RolesAllowed("ROLE_SUPER")
    public DeleteItemOutcome deleteAdmin(String id) {
        return table.deleteItem(
                new DeleteItemSpec()
                        .withPrimaryKey("id", id)
        );
    }

}
