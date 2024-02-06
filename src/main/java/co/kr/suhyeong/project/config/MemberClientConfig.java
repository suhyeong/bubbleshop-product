package co.kr.suhyeong.project.config;

import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.util.FeignClientUtil;
import feign.FeignException;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import io.netty.handler.codec.http.HttpStatusClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;

import static co.kr.suhyeong.project.constants.StaticValues.RESULT_CODE;
import static co.kr.suhyeong.project.constants.StaticValues.RESULT_MESSAGE;

@Slf4j
public class MemberClientConfig {

    @Bean
    public ErrorDecoder memberDecoder() {
        return (methodKey, response) -> {
            String resultCode = FeignClientUtil.getHeaderValue(response, RESULT_CODE);
            String resultMessage = FeignClientUtil.getHeaderValue(response, RESULT_MESSAGE);
            int httpStatus = FeignClientUtil.getResponseStatus(response);

            if (StringUtils.isAnyBlank(resultCode, resultMessage) && HttpStatusClass.SERVER_ERROR.contains(response.status())) {
                log.error(String.format("%s 요청이 성공하지 못했습니다. Retry 합니다. - status: %s, headers: %s", methodKey, response.status(), response.headers()));
            }

            log.error("MemberClient 호출시 에러 발생, methodKey : {}, resultCode : {}, resultMessage : {}, httpStatus : {}", methodKey, resultCode, resultMessage, httpStatus);

            if(StringUtils.isAnyBlank(resultCode, resultMessage))
                return FeignException.errorStatus(methodKey, response);

            return new ApiException(resultCode, resultMessage, httpStatus);
        };
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3);
    }
}
