package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetProductImageDetailRspDto {
    private Long id;
    private String divCode;
    private String path;
    private String fullUrl;
}
