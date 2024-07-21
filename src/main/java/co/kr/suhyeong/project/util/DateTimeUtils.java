package co.kr.suhyeong.project.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class DateTimeUtils {
    public static final String DATE_FORMAT_YYYY_MM_DD_DOT = "yyyy.MM.dd";

    public static String convertDateTimeToString(String pattern, LocalDateTime dateTime) {
        if(Objects.isNull(dateTime))
            return StringUtils.EMPTY;
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.KOREA));
    }
}
