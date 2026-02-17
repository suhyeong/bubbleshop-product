package com.bubbleshop.product.domain.model.view;

import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.entity.ProductImage;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MainProductView {
    private String productCode;
    private String productName;

    private int price;
    private int discountRate;

    private List<ProductFeatureView> featureTypes;
    private ProductImageView thumbnailImage;

    private LocalDateTime orderDeadlineDate;

    @QueryProjection
    public MainProductView(Product product) {
        this.productCode = product.getProductCode();
        this.productName = product.getProductName();
        this.price = product.getCost();
        this.discountRate = product.getDiscountRate();
        this.featureTypes = new ArrayList<>();
        product.getFeatures().forEach(feature -> featureTypes.add(new ProductFeatureView(feature)));
        product.getImages().stream().filter(ProductImage::isThumbnailImage).findFirst()
                .ifPresent(image -> this.thumbnailImage = new ProductImageView(image));
        this.orderDeadlineDate = product.getOrderDeadlineDate();
    }

    public void sortFeatureTypes() {
        this.featureTypes.sort(Comparator.comparingInt(f -> f.getFeatureType().getOrder()));
    }
}
