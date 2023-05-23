package co.kr.suhyeong.project.product.interfaces.rest.dto;

import co.kr.suhyeong.project.product.interfaces.rest.validator.CreateProductReqDtoValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@CreateProductReqDtoValidation
public class CreateProductReqDto {
    private String mainCategoryCode;
    private String subCategoryCode;
    private String type;
    private String name;
    private int price;
    private List<String> options;
    private String thumbnailImagePath;
    private String detailImagePath;
}
