package com.bubbleshop.util;

import com.bubbleshop.constants.StaticMessages;
import com.bubbleshop.constants.ValidationExceptionType;
import com.bubbleshop.constants.StaticValues;
import org.apache.commons.lang3.StringUtils;

public class MessageConvertUtils {

    /**
     * 에러타입에 따라 에러 메세지 치환
     * @param errorFieldName 에러필드 이름
     * @param exceptionType 에러 타입
     * @return 치환된 에러 메세지
     */
    public static String getErrorMessageReplace(String errorFieldName, ValidationExceptionType exceptionType) {
        switch (exceptionType) {
            case BLANK:
                return StaticMessages.BLANK_ERROR_MESSAGE.replace(StaticValues.REPLACE_FIRST_STRING, errorFieldName);
            default:
                return StringUtils.EMPTY;
        }
    }
}
