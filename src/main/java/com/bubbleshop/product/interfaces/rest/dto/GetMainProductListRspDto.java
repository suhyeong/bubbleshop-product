package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMainProductListRspDto {
    private String mainType;
    private List<GetMainProductRspDto> list;
}
