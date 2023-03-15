package co.kr.suhyeong.project.product.interfaces.rest.validator;

import co.kr.suhyeong.project.constants.StaticMessages;
import co.kr.suhyeong.project.constants.ValidationExceptionType;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static co.kr.suhyeong.project.constants.StaticValues.REPLACE_FIRST_STRING;

@Documented
@Constraint(validatedBy = CreateProductReqDtoValidation.Validator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 상품 생성 Request DTO Custom Validation
 */
public @interface CreateProductReqDtoValidation {
    String message() default "잘못된 요청 파라미터입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<CreateProductReqDtoValidation, CreateProductReqDto> {

        @Override
        public void initialize(CreateProductReqDtoValidation constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }

        @Override
        public boolean isValid(CreateProductReqDto value, ConstraintValidatorContext context) {
            return isProductNameValid(value.getName(), context)
                    && isCategoryCodeValid(value.getMainCategoryCode(), context)
                    && isCategoryCodeValid(value.getSubCategoryCode(), context);
        }

        /**
         * 상품명 Validation Check
         * @param productName 상품명
         * @param context
         * @return
         */
        public boolean isProductNameValid(String productName, ConstraintValidatorContext context) {
            boolean result = StringUtils.isNotBlank(productName);
            if(!result)
                addConstraintViolationException(context, this.getErrorMessageReplace("상품명", ValidationExceptionType.BLANK));
            return result;
        }

        /**
         * 카테고리 코드 Validation Check
         * @param categoryCode 카테고리 코드
         * @param context
         * @return
         */
        public boolean isCategoryCodeValid(String categoryCode, ConstraintValidatorContext context) {
            boolean result = StringUtils.isNotBlank(categoryCode);
            if(!result)
                addConstraintViolationException(context, this.getErrorMessageReplace("카테고리 코드", ValidationExceptionType.BLANK));
            return result;
        }

        /**
         * 에러타입에 따라 에러 메세지 치환
         * @param errorFieldName 에러필드 이름
         * @param exceptionType 에러 타입
         * @return
         */
        private String getErrorMessageReplace(String errorFieldName, ValidationExceptionType exceptionType) {
            switch (exceptionType) {
                case BLANK:
                    return StaticMessages.BLANK_ERROR_MESSAGE.replace(REPLACE_FIRST_STRING, errorFieldName);
                default:
                    return StringUtils.EMPTY;
            }
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
