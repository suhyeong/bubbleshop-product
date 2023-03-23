package co.kr.suhyeong.project.product.interfaces.rest.validator;

import co.kr.suhyeong.project.constants.ValidationExceptionType;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import co.kr.suhyeong.project.util.MessageConvertUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

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
                    && isCategoryCodeValid(value.getSubCategoryCode(), context)
                    && isTypeValid(value.getType(), context);
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
                addConstraintViolationException(context, MessageConvertUtils.getErrorMessageReplace("상품명", ValidationExceptionType.BLANK));
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
                addConstraintViolationException(context, MessageConvertUtils.getErrorMessageReplace("카테고리 코드", ValidationExceptionType.BLANK));
            return result;
        }

        /**
         * 상품 타입 Validation Check
         * @param type 상품 타입
         * @param context
         * @return
         */
        public boolean isTypeValid(String type, ConstraintValidatorContext context) {
            boolean result = StringUtils.isNotBlank(type);
            if(!result)
                addConstraintViolationException(context, MessageConvertUtils.getErrorMessageReplace("상품 타입", ValidationExceptionType.BLANK));
            return result;
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
