package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ModifyProductOptionReqDto {
    private int sequence;
    private String name;
    private int stockCnt;
    private boolean isDefaultOption;
}
