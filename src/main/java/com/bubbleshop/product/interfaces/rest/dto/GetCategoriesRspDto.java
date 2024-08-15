package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetCategoriesRspDto {
    private long totalCount;
    private List<GetCategoryRspDto> categoryList;
}
