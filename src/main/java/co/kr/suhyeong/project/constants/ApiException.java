package co.kr.suhyeong.project.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private String resultCode;
    private String resultMessage;
    private HttpStatus httpStatus;

    public ApiException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.resultCode = responseCode.getResponseCode();
        this.resultMessage = responseCode.getMessage();
        this.httpStatus = responseCode.getStatus();
    }
}
