package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.constants.ResponseCode;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static co.kr.suhyeong.project.constants.StaticValues.RESULT_CODE;
import static co.kr.suhyeong.project.constants.StaticValues.RESULT_MESSAGE;

public class BaseController {

    public HttpHeaders getSuccessHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RESULT_CODE, ResponseCode.SUCCESS.getResponseCode());
        headers.add(RESULT_MESSAGE, URLEncoder.encode(ResponseCode.SUCCESS.getMessage(), StandardCharsets.UTF_8));
        return headers;
    }

    public HttpHeaders getErrorHeaders(List<ObjectError> bindingErrors) {
        HttpHeaders headers = new HttpHeaders();
        bindingErrors.forEach(error -> {
            FieldError fieldError = (FieldError) error;
            headers.add(RESULT_CODE, ResponseCode.NON_EXIST_DATA.getResponseCode());
            headers.add(RESULT_MESSAGE, fieldError.getDefaultMessage());
        });

        return headers;
    }
}
