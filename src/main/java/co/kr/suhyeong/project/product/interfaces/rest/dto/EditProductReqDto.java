package co.kr.suhyeong.project.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditProductReqDto extends CreateProductReqDto {
    private String saleYn;
}
