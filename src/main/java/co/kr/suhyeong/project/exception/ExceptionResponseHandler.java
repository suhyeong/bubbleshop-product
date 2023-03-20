package co.kr.suhyeong.project.exception;

import co.kr.suhyeong.project.constants.ResponseCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.atomic.AtomicReference;

import static co.kr.suhyeong.project.constants.StaticValues.RESULT_CODE;
import static co.kr.suhyeong.project.constants.StaticValues.RESULT_MESSAGE;

@Slf4j
@RestControllerAdvice
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    private HttpHeaders getErrorHeader(ResponseCode responseCode, String customMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(RESULT_CODE, responseCode.getResponseCode());
        headers.add(RESULT_MESSAGE, StringUtils.defaultIfBlank(customMessage, responseCode.getMessage())); // TODO 인코딩 하지 않으면 한글 깨짐
        return headers;
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        ResponseCode responseCode = ResponseCode.INVALID_PARAMETER;
        AtomicReference<String> message = new AtomicReference<>(responseCode.getMessage());
        BindingResult bindingResult = ex.getBindingResult();

        bindingResult.getAllErrors().forEach(error -> {
            log.info("MethodArgumentNotValidException 에러 발생, 메세지 : {}", error.getDefaultMessage());
            if (StringUtils.isNotBlank(error.getDefaultMessage()))
                message.set(error.getDefaultMessage());
        });

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .responseCode(responseCode.getResponseCode())
                .responseMessage(message.get()).build();

        return new ResponseEntity<>(responseDto, getErrorHeader(responseCode, message.get()), responseCode.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ExceptionResponseDto> handleException(ConstraintViolationException exception) {
        log.info("ConstraintViolationException 에러 발생, 메세지 : {}", exception.getMessage());
        ResponseCode responseCode = ResponseCode.INVALID_PARAMETER;
        String message = responseCode.getMessage();

        if(StringUtils.isNotBlank(exception.getMessage())) {
            String[] messages = exception.getMessage().split(":");
            message = messages[messages.length-1].trim();
        }

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .responseCode(responseCode.getResponseCode())
                .responseMessage(message).build();

        return new ResponseEntity<>(responseDto, getErrorHeader(responseCode, message), responseCode.getStatus());
    }

    // MethodArgumentNotValidException 에 대해 따로 선언하여 사용하는 방법 (ResponseEntityExceptionHandler 을 상속받지 않는 경우 사용 가능)
    /*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponseDto> handleException(BindException bindException) {
        ResponseCode responseCode = ResponseCode.INVALID_PARAMETER;
        AtomicReference<String> message = new AtomicReference<>(responseCode.getMessage());
        BindingResult bindingResult = bindException.getBindingResult();

        bindingResult.getAllErrors().forEach(error -> {
            log.info("MethodArgumentNotValidException 에러 발생, 메세지 : {}", error.getDefaultMessage());
            if (StringUtils.isNotBlank(error.getDefaultMessage()))
                message.set(error.getDefaultMessage());
        });

        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .responseCode(responseCode.getResponseCode())
                .responseMessage(message.get()).build();

        return new ResponseEntity<>(responseDto, getErrorHeader(responseCode, message.get()), responseCode.getStatus());
    }
     */
}
