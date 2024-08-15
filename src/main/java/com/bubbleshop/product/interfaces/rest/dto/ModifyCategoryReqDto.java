package com.bubbleshop.product.interfaces.rest.dto;

import com.bubbleshop.product.interfaces.rest.validator.ModifyCategoryReqDtoValidation;
import lombok.Getter;

@Getter
@ModifyCategoryReqDtoValidation
public class ModifyCategoryReqDto {
    private String categoryName;
    private String categoryEngName;
    private String categoryType;
    private boolean isShow;
}
