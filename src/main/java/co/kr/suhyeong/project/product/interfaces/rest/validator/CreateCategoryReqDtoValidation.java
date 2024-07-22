package co.kr.suhyeong.project.product.interfaces.rest.validator;

import co.kr.suhyeong.project.constants.ValidationExceptionType;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateCategoryReqDto;
import co.kr.suhyeong.project.util.MessageConvertUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CreateCategoryReqDtoValidation.Validator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 카테고리 생성 Request DTO Custom Validation
 */
public @interface CreateCategoryReqDtoValidation {
    String message() default "잘못된 요청 파라미터입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<CreateCategoryReqDtoValidation, CreateCategoryReqDto> {

        @Override
        public void initialize(CreateCategoryReqDtoValidation constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(CreateCategoryReqDto value, ConstraintValidatorContext context) {
            if(StringUtils.isBlank(value.getCategoryCode())) {
                addConstraintViolationException(context, MessageConvertUtils.getErrorMessageReplace("카테고리 코드", ValidationExceptionType.BLANK));
                return false;
            }

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
