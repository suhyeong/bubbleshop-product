package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductImageDetailRspDto {
    private int sequence;
    private String divCode;
    private String path;
    private String fullUrl;
}
