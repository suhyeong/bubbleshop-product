package com.bubbleshop.product.interfaces.rest.dto;

import com.bubbleshop.product.interfaces.rest.validator.CreateCategoryReqDtoValidation;
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
