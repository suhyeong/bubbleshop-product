package com.bubbleshop.product.domain.repository;

import com.bubbleshop.product.domain.constant.ProductImageCode;
import com.bubbleshop.product.domain.model.entity.ProductImage;
import com.bubbleshop.product.domain.model.entity.ProductImageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProduct_ProductCodeAndDivCodeIn(String productCode, List<ProductImageCode> imageCodes);
}
