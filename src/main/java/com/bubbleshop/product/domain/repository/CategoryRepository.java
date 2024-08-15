package com.bubbleshop.product.domain.repository;

import com.bubbleshop.product.domain.model.aggregate.Category;
import com.bubbleshop.product.infrastructure.jpa.CategoryCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String>, CategoryCustomRepository {
}
