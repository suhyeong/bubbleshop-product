package co.kr.suhyeong.project.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {
    SUCCESS("00", "00", "정상적으로 성공하였습니다.", HttpStatus.OK),
    INVALID_PARAMETER("00", "03", "잘못된 파라미터입니다.", HttpStatus.BAD_REQUEST),
    NON_EXIST_DATA("00", "04", "데이터가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    SERVER_ERROR("00", "99", "에러가 발생하였습니다. 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR),

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
