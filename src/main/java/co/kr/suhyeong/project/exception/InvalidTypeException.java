package co.kr.suhyeong.project.exception;

import co.kr.suhyeong.project.constants.ResponseCode;

public class InvalidTypeException extends ApiException {
    public InvalidTypeException(ResponseCode responseCode) {
        super(responseCode);
    }
}
