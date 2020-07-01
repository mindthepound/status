package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LogUtil {

    private DateUtil dateUtil;

    @Autowired
    public LogUtil(
            DateUtil dateUtil
    ) {
        this.dateUtil = dateUtil;
    }

    public Map<String, Long> pastLogs(Map<String, Long> logs) throws ParseException {

        Map<String, Long> pastLogs = new HashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH");

        Date now = simpleDateFormat.parse(dateUtil.toISOStringForKSTWithHour(new Date()));

        for (Map.Entry<String, Long> log : logs.entrySet()) {

            Date logDate = simpleDateFormat.parse(log.getKey().substring(0, log.getKey().indexOf(":")));

            if (logDate.compareTo(now) < 0)
                pastLogs.put(log.getKey(), log.getValue());
        }

        return pastLogs;
    }

    public Integer countContainColon(String data) {
        return data.length() - data.replaceAll(":","").length();
    }

    public HashMap<String, Long> getADTimes(Map<String, Long> logs) {
        return getLongData(logs, 1);
    }

    public HashMap<String, Long> getADIDS(Map<String, Long> logs) {
        return getLongData(logs, 2);
    }

    public HashMap<String, Long> getTypes(Map<String, Long> logs) { return getLongData(logs, 3); }

    public Long getCount(String key, Map<String, Long> logs) {

        for (Map.Entry<String, Long> log : logs.entrySet()) {

            if (log.getKey().equals(key)) return Long.parseLong(String.valueOf(log.getValue()));

        }

        return 0L;

    }

    private HashMap<String, Long> getLongData(Map<String, Long> logs, Integer position) {

        HashMap<String, Long> datas = new HashMap<>();

        for (Map.Entry<String, Long> log : logs.entrySet()) {

            String key = log.getKey();
            String value = null;
            switch (position) {
                case 1 :
                    value = key.substring(0, key.indexOf(":"));
                    break;
                case 2 :
                    value = key.substring(key.indexOf(":") + 1, key.lastIndexOf(":"));
                    break;
                case 3 :
                    value = key.substring(key.lastIndexOf(":") + 1, key.length());
                    break;
                default:
                    break;
            }

            if (!datas.containsKey(value)) datas.put(value, log.getValue());
        }

        return datas;
    }

}
