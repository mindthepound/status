package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PasswordUtil {

    public String encryptPassword(PasswordEncoder passwordEncoder, String text) {
        return passwordEncoder.encode(text);
    }

}
