package io.spin.status.util.aws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DynamoDBHelper {

    @Value("${spring.profiles.active}")
    private String environment;

    public DynamoDBMapperConfig getTestEnvironmentConfig() {

        if (environment.equalsIgnoreCase("production"))
            return null;
        else
            return new DynamoDBMapperConfig
                        .Builder()
                        .withTableNameOverride(
                                DynamoDBMapperConfig.
                                        TableNameOverride
                                        .withTableNamePrefix("test.")
                        )
                        .build();
    }

    public DynamoDBMapperConfig getUpdateConfig() {
        return new DynamoDBMapperConfig
                .Builder()
                .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                .build();
    }

    public DynamoDBMapperConfig getConsistentConfig() {
        return new DynamoDBMapperConfig
                .Builder()
                .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                .build();
    }

    public DynamoDBMapperConfig getConsistentEnvironmentConfig() {
        if (environment.equalsIgnoreCase("production")) {
            return new DynamoDBMapperConfig
                    .Builder()
                    .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                    .build();
        } else {
            return new DynamoDBMapperConfig
                    .Builder()
                    .withTableNameOverride(
                            DynamoDBMapperConfig.
                                    TableNameOverride
                                    .withTableNamePrefix("test.")
                    )
                    .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                    .build();
        }
    }
    public DynamoDBMapperConfig getUpdateEnvironmentConfig() {
        if (environment.equalsIgnoreCase("production")) {
            return new DynamoDBMapperConfig
                    .Builder()
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                    .build();
        }
        else {
            return new DynamoDBMapperConfig
                    .Builder()
                    .withTableNameOverride(
                            DynamoDBMapperConfig.
                                    TableNameOverride
                                    .withTableNamePrefix("test.")
                    )
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES)
                    .build();
        }

    }

}
