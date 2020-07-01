package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NullCheckUtil {

    public Integer checkInteger(Integer data) {
        if (data == null) return 0;
        else return data;
    }

    public String checkString(String data) {
        if (data == null) return "";
        else return data;
    }

}
