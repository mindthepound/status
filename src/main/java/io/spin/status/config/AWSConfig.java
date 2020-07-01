package io.spin.status.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudfront.AmazonCloudFront;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClientBuilder;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AWSConfig {

    @Value("${AWS_ACCESS_KEY_ID}")
    private String accessKey;

    @Value("${AWS_SECRET_KEY}")
    private String secretKey;

    @Value("${spring.aws.region}")
    private String region;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider(AWSCredentials awsCredentials) {

        return new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return awsCredentials;
            }

            @Override
            public void refresh() {
            }
        };
    }

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentialsProvider awsCredentialsProvider) {

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.fromName(region))
                .build();
    }

    @Bean
    public AmazonCloudFront amazonCloudFront(AWSCredentialsProvider awsCredentialsProvider) {

        return AmazonCloudFrontClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.fromName(region))
                .build();
    }

    @Bean
    public AmazonCloudWatch amazonCloudWatch(AWSCredentialsProvider awsCredentialsProvider) {

        return AmazonCloudWatchClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.fromName(region))
                .build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {

        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.fromName(region))
                .build();
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService(AWSCredentialsProvider awsCredentialsProvider) {

        return AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {

        return new DynamoDBMapper(amazonDynamoDB);
    }

}
