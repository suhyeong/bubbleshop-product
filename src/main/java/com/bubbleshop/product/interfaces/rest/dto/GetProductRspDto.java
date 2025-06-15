package com.bubbleshop.product.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class GetProductRspDto {
    private String productCode;
    private String productName;
    private String productEngName;
    private String createdAt;

    private String mainCategoryCode;
    private String mainCategoryName;
    private String subCategoryCode;
    private String subCategoryName;

    private int price;
    private int discountRate;
    private Boolean isSale;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GetProductImageDetailRspDto> imageList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GetProductFeatureRspDto> features;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GetProductOptionRspDto> options;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Integer> points;
}
