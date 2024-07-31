package co.kr.suhyeong.project.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductOptionRspDto {
    private int sequence;
    private String name;
    private Boolean isDefaultOption;
    private int stockCnt;
}