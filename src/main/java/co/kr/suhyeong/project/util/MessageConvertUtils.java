package co.kr.suhyeong.project.util;

import co.kr.suhyeong.project.constants.StaticMessages;
import co.kr.suhyeong.project.constants.ValidationExceptionType;
import org.apache.commons.lang3.StringUtils;

import static co.kr.suhyeong.project.constants.StaticValues.REPLACE_FIRST_STRING;

public class MessageConvertUtils {

    /**
     * 에러타입에 따라 에러 메세지 치환
     * @param errorFieldName 에러필드 이름
     * @param exceptionType 에러 타입
     * @return
     */
    public static String getErrorMessageReplace(String errorFieldName, ValidationExceptionType exceptionType) {
        switch (exceptionType) {
            case BLANK:
                return StaticMessages.BLANK_ERROR_MESSAGE.replace(REPLACE_FIRST_STRING, errorFieldName);
            default:
                return StringUtils.EMPTY;
        }
    }
}
