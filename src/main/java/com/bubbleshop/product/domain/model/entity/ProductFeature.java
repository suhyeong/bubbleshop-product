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

    @MapsId("productCode")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    @ToString.Exclude
    private Product product;

    public ProductFeature(Product product) {
        this.product = product;
        this.productFeatureId = new ProductFeatureId();
    }

    public void applyFeatureType(FeatureType featureType) {
        this.productFeatureId.applyFeatureType(featureType);
    }
}
