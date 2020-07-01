package io.spin.status.util.aws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class CustomNumberDynamoDBTypeConverter implements DynamoDBTypeConverter<String, Integer> {
    @Override
    public String convert(Integer object) { return String.valueOf(object); }

    @Override
    public Integer unconvert(String object) {
        return (int)Math.ceil(Float.parseFloat(object));
    }
}
