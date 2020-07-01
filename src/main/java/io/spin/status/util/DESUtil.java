package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Slf4j
@Service
public class DESUtil {

    @Value("${spin.des.key}")
    private String secretKey;
    private String algorithm = "DES/ECB/PKCS5Padding";
    private Cipher cipher;
    private DESKeySpec desKeySpec;
    private SecretKeyFactory secretKeyFactory;
    private Key key;

    public String encrypt(String text) {

        try {
            init();

            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(Base64.getEncoder().encode(cipher.doFinal(text.getBytes())), "UTF-8");
        } catch (InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException |
                UnsupportedEncodingException e) {
        }
        return "";
    }

    public String decrypt(String text) {

        try {
            init();

            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(text.getBytes())), "UTF-8");
        } catch (
                InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException |
                UnsupportedEncodingException e) {
        }
        return "";
    }

    private void init() {
        try {
            cipher = Cipher.getInstance(algorithm);
            desKeySpec = new DESKeySpec(secretKey.getBytes());
            secretKeyFactory = SecretKeyFactory.getInstance("DES");
            key = secretKeyFactory.generateSecret(desKeySpec);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                InvalidKeySpecException e) {
        }
    }
}
