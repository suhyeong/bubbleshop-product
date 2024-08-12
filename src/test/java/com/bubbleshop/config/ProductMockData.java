package com.bubbleshop.config;

import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.entity.ProductImage;

import java.util.List;
import java.util.Set;

public class ProductMockData {
    public static Product createProduct(String code, List<ProductImage> images) {
        return Product.builder()
                .productCode(code)
                .productName("테스트")
                .mainCategoryCode("01")
                .subCategoryCode("01")
                .cost(100000)
                .images(images)
                .featureTypes(Set.of())
                .options(List.of())
                .build();
    }
}
