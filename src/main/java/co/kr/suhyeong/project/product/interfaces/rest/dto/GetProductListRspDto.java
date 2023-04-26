package co.kr.suhyeong.project.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductListRspDto {
    private String productId;
    private String productName;
    private int price;
}
