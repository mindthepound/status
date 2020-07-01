package io.spin.status.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Component
public class DateUtil {

    private String DATE_ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    private String DATE_ISO8601_FORMAT_HOUR = "yyyy-MM-dd'T'HH";
    private String DATE_NORMAL_FORMAT = "yyyy-MM-dd";
    private String DATE_MONTH_FORMAT = "yyyy-MM";

    public String toISOString(Date date) {
        DateFormat df = new SimpleDateFormat(DATE_ISO8601_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }
    public String toISOString(String date){
        DateFormat df = new SimpleDateFormat(DATE_ISO8601_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    public String toISOStringWithHour(Date date) {
        DateFormat df = new SimpleDateFormat(DATE_ISO8601_FORMAT_HOUR);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }
    public String toISOStringWithHour(String date) {
        DateFormat df = new SimpleDateFormat(DATE_ISO8601_FORMAT_HOUR);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    public String toISOStringForKSTWithHour(Date date) { return new SimpleDateFormat(DATE_ISO8601_FORMAT_HOUR).format(date); }
    public String toISOStringForKSTWithHour(String date) { return new SimpleDateFormat(DATE_ISO8601_FORMAT_HOUR).format(date); }

    public String todayKorean() {
        return new SimpleDateFormat("yyyy년 MM월 dd일").format(new Date());
    }

    public String today() { return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(new Date()); }

    public String yesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -12);
        return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(calendar.getTime());
    }

    public String tomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(calendar.getTime());
    }

    public String oneWeekAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(calendar.getTime());
    }

    public String thisMonthFirst() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, - getToday() + 1);
        return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(calendar.getTime());
    }

    public String thisMonthLast() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +1);
        calendar.add(Calendar.DATE, - getToday() + 1);
        return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(calendar.getTime());
    }

    public String MonthFirst(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DATE, 1);
        return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(calendar.getTime());
    }

    public String MonthLast(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        return new SimpleDateFormat(DATE_NORMAL_FORMAT).format(calendar.getTime());
    }
    public String monthAgo(Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -month);
        return new SimpleDateFormat(DATE_MONTH_FORMAT).format(calendar.getTime());
    }

    private Integer getToday() {
        return Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
    }

    public String yearsLater(String date, Integer year) {
        if (date == null) {
            return yearsLater(year);
        }
        else {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new SimpleDateFormat(DATE_ISO8601_FORMAT).parse(date));
                calendar.add(Calendar.YEAR, +year);
                return new SimpleDateFormat(DATE_ISO8601_FORMAT).format(calendar.getTime());
            } catch (ParseException e) {
                return yearsLater(year);
            }
        }
    }

    private String yearsLater(Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, +year);
        return new SimpleDateFormat(DATE_ISO8601_FORMAT).format(calendar.getTime());
    }
}
