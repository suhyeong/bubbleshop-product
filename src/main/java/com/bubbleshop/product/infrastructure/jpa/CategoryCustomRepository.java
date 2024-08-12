package com.bubbleshop.product.infrastructure.jpa;

import com.bubbleshop.product.domain.command.GetCategoriesCommand;
import com.bubbleshop.product.domain.model.aggregate.Category;

import java.util.List;

public interface CategoryCustomRepository {
    List<Category> findCategories(GetCategoriesCommand command);
    long countCategories(GetCategoriesCommand command);
}
