package com.bubbleshop.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class DateTimeUtils {
    public static final String DATE_FORMAT_YYYY_MM_DD_DOT = "yyyy.MM.dd";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String convertDateTimeToString(String pattern, LocalDateTime dateTime) {
        if(Objects.isNull(dateTime))
            return StringUtils.EMPTY;
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.KOREA));
    }

    public static LocalDateTime convertStringToLocalDateTime(String datetime) {
        if(Objects.isNull(datetime))
            return null;
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
    }
}
