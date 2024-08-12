package com.bubbleshop.product.interfaces.rest.dto;

import com.bubbleshop.product.interfaces.rest.validator.CreateProductReqDtoValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@CreateProductReqDtoValidation
@ToString
public class CreateProductReqDto {
    private String mainCategoryCode;
    private String subCategoryCode;
    private List<String> features;
    private String name;
    private String engName;
    private int price;
    private List<String> options;
    private String defaultOption;
    private String thumbnailImageName;
    private List<String> detailImageName;
}
