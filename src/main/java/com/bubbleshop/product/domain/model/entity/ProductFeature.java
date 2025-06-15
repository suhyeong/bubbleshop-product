package com.bubbleshop.product.domain.model.entity;

import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.model.aggregate.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_features_master")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductFeature extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -851655339693904946L;

    @EmbeddedId
    private ProductFeatureId productFeatureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    @ToString.Exclude
    private Product product;

    public ProductFeature(String productCode, FeatureType featureType) {
        this.productFeatureId = new ProductFeatureId(productCode, featureType);
    }
}
