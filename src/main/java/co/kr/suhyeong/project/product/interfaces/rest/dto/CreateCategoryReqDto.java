package co.kr.suhyeong.project.product.interfaces.rest.dto;

import co.kr.suhyeong.project.product.interfaces.rest.validator.CreateCategoryReqDtoValidation;
import lombok.Getter;

@Getter
@CreateCategoryReqDtoValidation
public class CreateCategoryReqDto {
    private String categoryCode;
    private String categoryName;
    private String categoryEngName;
    private String categoryType;
    private boolean isShow;
}
