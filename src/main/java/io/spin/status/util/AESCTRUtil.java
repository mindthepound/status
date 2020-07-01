package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class AESCTRUtil {

    private ExternalHttpRequest externalHttpRequest;

    @Value("${spin.aes.url}")
    private String aesServerUrl;

    @Autowired
    public AESCTRUtil(
            ExternalHttpRequest externalHttpRequest
    ) {
        this.externalHttpRequest = externalHttpRequest;
    }

    public String encrypt(String text, String type) {
        return externalHttpRequest.post(
                generateHttpPost(
                        aesServerUrl +"/v1/aes/encrypt",
                        generateNameValuePair(text, type)
                )
        ).replaceAll("\"","");
    }

    public String decrypt(String text, String type) {
        return externalHttpRequest.post(
                generateHttpPost(
                        aesServerUrl +"/v1/aes/decrypt",
                        generateNameValuePair(text, type)
                )
        ).replaceAll("\"","");
    }

    private HttpPost generateHttpPost(String url, List<NameValuePair> nameValuePairList) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));

            return httpPost;
        } catch (UnsupportedEncodingException e) { }
        return null;
    }

    private List<NameValuePair> generateNameValuePair(String text, String type) {
        return new ArrayList<>(
                Arrays.asList(
                        new BasicNameValuePair("text", text),
                        new BasicNameValuePair("type", type)
                )
        );
    }

}
