package co.kr.suhyeong.project.product.interfaces.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyProductReqDto extends CreateProductReqDto {
    private Double discount;
    private String saleYn;
}
