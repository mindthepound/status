package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExternalHttpRequest {

    ResponseHandler<String> responseHandler = (HttpResponse response) -> {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity httpEntity = response.getEntity();
            return httpEntity != null ? EntityUtils.toString(httpEntity) : null;
        } else
            throw new ClientProtocolException("Unexcepted response status : " + status);
    };

    public String get(
            HttpGet httpGet
    ) {
        return request(HttpClients.createDefault(), httpGet);
    }

    public String post(
            HttpPost httpPost
    ) {
        return request(HttpClients.createDefault(), httpPost);
    }

    public String put(
            HttpPut httpPut
    ) {
        return request(HttpClients.createDefault(), httpPut);
    }

    public String delete(
            HttpDelete httpDelete
    ) {
        return request(HttpClients.createDefault(), httpDelete);
    }

    private String request(
            CloseableHttpClient closeableHttpClient,
            HttpUriRequest httpUriRequest
    ) {
        try {
            String response = closeableHttpClient.execute(httpUriRequest, responseHandler);
            closeableHttpClient.close();

            return response;
        } catch (Exception e) {
            log.error("ExternalHttpRequest");
            log.error("message : " + e.getMessage());
            log.error("stack : ");
            e.printStackTrace();
        }
        return null;
    }

}
