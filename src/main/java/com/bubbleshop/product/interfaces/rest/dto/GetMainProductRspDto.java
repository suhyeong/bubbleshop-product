package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMainProductRspDto {
    private String productCode;
    private String productName;

    private int price;
    private int discountRate;
    private String orderDeadlineDate;

    private GetProductImageDetailRspDto image;
    private List<GetProductFeatureRspDto> features;
}
