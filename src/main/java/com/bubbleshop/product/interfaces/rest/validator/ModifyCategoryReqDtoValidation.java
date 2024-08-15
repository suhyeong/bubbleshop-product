package com.bubbleshop.product.interfaces.rest.validator;

import com.bubbleshop.constants.ValidationExceptionType;
import com.bubbleshop.product.interfaces.rest.dto.ModifyCategoryReqDto;
import com.bubbleshop.util.MessageConvertUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ModifyCategoryReqDtoValidation.Validator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 카테고리 수정 Request DTO Custom Validation
 */
public @interface ModifyCategoryReqDtoValidation {
    String message() default "잘못된 요청 파라미터입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ModifyCategoryReqDtoValidation, ModifyCategoryReqDto> {

        @Override
        public void initialize(ModifyCategoryReqDtoValidation constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(ModifyCategoryReqDto value, ConstraintValidatorContext context) {
            if(StringUtils.isBlank(value.getCategoryName())) {
                addConstraintViolationException(context, MessageConvertUtils.getErrorMessageReplace("카테고리명", ValidationExceptionType.BLANK));
                return false;
            }

            if(StringUtils.isBlank(value.getCategoryEngName())) {
                addConstraintViolationException(context, MessageConvertUtils.getErrorMessageReplace("카테고리 영문명", ValidationExceptionType.BLANK));
                return false;
            }

            if(StringUtils.isBlank(value.getCategoryType())) {
                addConstraintViolationException(context, MessageConvertUtils.getErrorMessageReplace("카테고리 타입", ValidationExceptionType.BLANK));
                return false;
            }

            return true;
        }

        /**
         * exception 에러를 전달할 수 있도록 세팅
         * @param context
         * @param errorMessage 에러 메세지
         */
        private void addConstraintViolationException(ConstraintValidatorContext context, String errorMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addConstraintViolation();
        }
    }
}
