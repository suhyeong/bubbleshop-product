package com.bubbleshop.product.domain.model.view;

import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.model.entity.ProductFeature;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductFeatureView {
    private FeatureType featureType;

    public ProductFeatureView(ProductFeature feature) {
        this.featureType = feature.getProductFeatureId().getFeatureType();
    }
}
