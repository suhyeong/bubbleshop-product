package com.bubbleshop.product.domain.command;

import com.bubbleshop.product.domain.constant.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyProductCommand {
    private String productCode;

    private String name;
    private String engName;
    private int price;
    private Set<FeatureType> featureTypes;
    private int discount;
    private boolean isSale;
    private Set<ProductOption> options;

    @Builder
    @Getter
    public static class ProductOption {
        private int sequence;
        private String name;
        private int stockCnt;
        private boolean isDefaultOption;
    }
}
