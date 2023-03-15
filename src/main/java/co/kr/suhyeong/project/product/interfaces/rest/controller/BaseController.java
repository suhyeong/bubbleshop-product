package co.kr.suhyeong.project.product.interfaces.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.net.URL;
import java.util.List;

import static co.kr.suhyeong.project.constants.StaticValues.RESULT_CODE;
import static co.kr.suhyeong.project.constants.StaticValues.RESULT_MESSAGE;

public class BaseController {

    public HttpHeaders getSuccessHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RESULT_CODE, "0000");
        headers.add(RESULT_MESSAGE, "성공");
        return headers;
    }

    public HttpHeaders getErrorHeaders(List<ObjectError> bindingErrors) {
        HttpHeaders headers = new HttpHeaders();
        bindingErrors.forEach(error -> {
            FieldError fieldError = (FieldError) error;
            headers.add(RESULT_CODE, "0004");
            headers.add(RESULT_MESSAGE, fieldError.getDefaultMessage());
        });

        return headers;
    }
}
