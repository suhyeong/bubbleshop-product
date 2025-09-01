package com.bubbleshop.product.domain.command;

import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.constant.PointType;
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
    private Set<ProductPoint> points;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductOption {
        private int sequence;
        private String name;
        private int stockCnt;
        private boolean isDefaultOption;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductPoint {
        private PointType productType;
        private int savePoint;
    }
}
