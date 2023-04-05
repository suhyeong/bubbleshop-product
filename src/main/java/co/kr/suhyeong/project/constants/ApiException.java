package co.kr.suhyeong.project.constants;

public class ApiException extends RuntimeException {
    public ApiException(ResponseCode responseCode) {
        super(responseCode.getMessage());
    }
}
