package co.kr.suhyeong.project.product.application.internal.queryservice;

import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Category;
import co.kr.suhyeong.project.product.domain.model.view.CategoryView;
import co.kr.suhyeong.project.product.domain.repository.CategoryRepository;
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
    public List<CategoryView> getCategories(GetCategoriesCommand command) {
        List<CategoryView> views = new ArrayList<>();
        if(command.isCategoryCodeExist()) {
            categoryRepository.findById(command.getCategoryCode())
                    .ifPresent(category -> views.add(new CategoryView(category)));
            return views;
        }

        List<Category> categories = categoryRepository.findCategories(command);
        categories.forEach(category -> views.add(new CategoryView(category)));
        return views;
    }
}
