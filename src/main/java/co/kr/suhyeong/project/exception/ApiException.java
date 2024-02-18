package co.kr.suhyeong.project.exception;

import co.kr.suhyeong.project.constants.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private String resultCode;
    private String resultMessage;
    private int httpStatus;

    public ApiException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.resultCode = responseCode.getResponseCode();
        this.resultMessage = responseCode.getMessage();
        this.httpStatus = responseCode.getStatus().value();
    }

    public ApiException(String resultCode, String resultMessage, int httpStatus) {
        super(resultMessage);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.httpStatus = httpStatus;
    }

}
