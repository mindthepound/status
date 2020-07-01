package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Slf4j
@Component
public class StringUtil {

    public boolean isNumeric(String object) {
        return object.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean isNullOrEmpty(Object object) {
        if (object == null) return true;
        return String.valueOf(object).equalsIgnoreCase("");
    }

    public String convertJSONForDynamoDB(String data) {

        if (data == null) return null;

        return data.replaceAll("\\\\", "");
    }

    public String numberWithCommas(Long num) {

        if (num == null) return null;

        DecimalFormat dc = new DecimalFormat("###,###,###,###");
        return dc.format(num);
    }
}
