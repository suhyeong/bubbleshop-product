package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProductListRspDto {
    private long totalCount;
    private List<GetProductRspDto> productList;
}
