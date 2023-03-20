package co.kr.suhyeong.project.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    INVALID_PARAMETER("00", "03", "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),

    ;

    private final String cate;
    private final String code;
    private final String message;
    private final HttpStatus status;

    ResponseCode(String cate, String code, String message, HttpStatus status) {
        this.cate = cate;
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getResponseCode() {
        return this.cate + this.code;
    }

}
