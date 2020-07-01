package io.spin.status.util;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JSONUtil {

    public String mapToJSON(Map map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(map);
    }

    public Map<String, Object> jsonToMap(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
    }

    public String objectToJsonString(Object object) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(object);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, AttributeValue> jsonToDynamoDBPageMap(String json) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
            Map<String, AttributeValue> returnData = new HashMap<>();

            for (Map.Entry data : map.entrySet()) {

                String dataValues = String.valueOf(data.getValue());
                dataValues = dataValues.substring(1, dataValues.length() - 1);
                for (String dataValue: dataValues.split(",")) {
                    String key = String.valueOf(dataValue).substring(0, dataValue.indexOf("="));
                    String value = String.valueOf(dataValue).substring(dataValue.indexOf("=") + 1, dataValue.length());

                    switch (key) {
                        case "s" :
                            returnData.put(
                                    String.valueOf(data.getKey()),
                                    new AttributeValue().withS(value)
                            );
                            break;
                        case "n" :
                            returnData.put(
                                    String.valueOf(data.getKey()),
                                    new AttributeValue().withN(value)
                            );
                            break;
                        case "bOOL" :
                            returnData.put(
                                    String.valueOf(data.getKey()),
                                    new AttributeValue().withBOOL(Boolean.valueOf(value))
                            );
                            break;
                        default:
                            break;
                    }
                }
            }

            return returnData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
