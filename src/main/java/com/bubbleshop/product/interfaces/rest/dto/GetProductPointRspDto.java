package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductPointRspDto {
    private String pointTypeCode;
    private int savePoint;
}