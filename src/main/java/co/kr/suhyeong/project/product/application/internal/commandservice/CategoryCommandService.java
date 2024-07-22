package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.constants.ResponseCode;
import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.CreateCategoryCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyCategoryCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Category;
import co.kr.suhyeong.project.product.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCommandService {
    private final CategoryRepository categoryRepository;

    @Transactional(rollbackFor = Exception.class)
    public void createCategory(CreateCategoryCommand command) {
        Category category = new Category(command.getCategoryCode(), command.getCategoryName(), command.getCategoryEngName(), command.getCategoryType(), command.isShow());
        categoryRepository.save(category);
    }

    @Transactional(rollbackFor = Exception.class)
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
