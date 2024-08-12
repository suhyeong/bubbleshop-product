package com.bubbleshop.product.application.internal.queryservice;

import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.product.domain.command.GetCategoriesCommand;
import com.bubbleshop.product.domain.model.aggregate.Category;
import com.bubbleshop.product.domain.model.view.CategoryListView;
import com.bubbleshop.product.domain.model.view.CategoryView;
import com.bubbleshop.product.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService {
    private final CategoryRepository categoryRepository;

    @Cacheable(cacheNames = StaticValues.RedisKey.CATEGORY_KEY,
            key = "#command.categoryType.name().toLowerCase() + '_cate'",
            condition = "!#command.needToPaging && #command.categoryTypeExist")
    public CategoryListView getCategories(GetCategoriesCommand command) {
        List<CategoryView> views = new ArrayList<>();
        if(command.isCategoryCodeExist()) {
            categoryRepository.findById(command.getCategoryCode())
                    .ifPresent(category -> views.add(new CategoryView(category)));
            return new CategoryListView(views);
        }

        List<Category> categories = categoryRepository.findCategories(command);
        categories.forEach(category -> views.add(new CategoryView(category)));

        if(command.isNeedToPaging()) {
            long count = categoryRepository.countCategories(command);
            return new CategoryListView(count, views);
        } else return new CategoryListView(views);
    }
}
