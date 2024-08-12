package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductFeatureRspDto {
    private String code;
    private String desc;
}
