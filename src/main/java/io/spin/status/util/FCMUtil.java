package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FCMUtil {

    private ExternalHttpRequest externalHttpRequest;

    @Value("${spring.fcm.api-key}")
    private String apiKey;

    @Value("${spring.fcm.url}")
    private String fcmServerUrl;

    @Autowired
    public FCMUtil(
            ExternalHttpRequest externalHttpRequest
    ) {
        this.externalHttpRequest = externalHttpRequest;
    }

    public void send(String data) {
        externalHttpRequest.post(generateHttpPost(data));
    }

    private HttpPost generateHttpPost(String data) {
        HttpPost httpPost = new HttpPost(fcmServerUrl);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "key="+apiKey);
        httpPost.setEntity(new StringEntity(data, "utf-8"));

        return httpPost;
    }
}
