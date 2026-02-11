package com.bubbleshop.product.domain.model.entity;

import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.model.converter.ProductFeaturesTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jdk.jfr.Description;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductFeatureId implements Serializable {
    @Serial
    private static final long serialVersionUID = -7449202244134966655L;

    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("태그(특징) 코드")
    @Column(name = "feature_code")
    @Convert(converter = ProductFeaturesTypeConverter.class)
    private FeatureType featureType;

    public void applyFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }
}
