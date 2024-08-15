package com.bubbleshop.product.domain.repository;

import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.infrastructure.jpa.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String>, ProductCustomRepository {
    int countByMainCategoryCodeAndSubCategoryCode(String mainCategoryCode, String subCategoryCode);
}
