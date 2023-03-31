package co.kr.suhyeong.project.product.interfaces.rest.dto;

import co.kr.suhyeong.project.product.interfaces.rest.validator.CreateProductReqDtoValidation;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@CreateProductReqDtoValidation
public class CreateProductReqDto {
    private String mainCategoryCode;
    private String subCategoryCode;
    private String type;
    private String name;
    private int price;
    private List<String> options;
}
