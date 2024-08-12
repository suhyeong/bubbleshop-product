package com.bubbleshop.product.application.internal.commandservice;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.product.domain.command.CreateCategoryCommand;
import com.bubbleshop.product.domain.command.ModifyCategoryCommand;
import com.bubbleshop.product.domain.model.aggregate.Category;
import com.bubbleshop.product.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {
    private final CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = StaticValues.RedisKey.CATEGORY_KEY,
            key = "#command.categoryType.name().toLowerCase() + '_cate'")
    public void createCategory(CreateCategoryCommand command) {
        Category category = new Category(command.getCategoryCode(), command.getCategoryName(), command.getCategoryEngName(), command.getCategoryType(), command.isShow());
        categoryRepository.save(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = StaticValues.RedisKey.CATEGORY_KEY,
            key = "#command.categoryType.name().toLowerCase() + '_cate'")
    public void modifyCategory(ModifyCategoryCommand command) {
        Category category = categoryRepository.findById(command.getCategoryCode())
                .orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        category.modifyCategoryInfo(command);
        categoryRepository.save(category);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(String categoryCode) {
        Category category = categoryRepository.findById(categoryCode)
                .orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        categoryRepository.delete(category);
    }
}
