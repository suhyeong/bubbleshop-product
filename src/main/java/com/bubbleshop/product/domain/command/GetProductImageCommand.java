package com.bubbleshop.product.domain.command;

import com.bubbleshop.product.domain.constant.ProductImageCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProductImageCommand {
    private String productCode;
    private List<ProductImageCode> productImageCodeList;
}
