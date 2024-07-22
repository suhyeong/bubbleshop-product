package co.kr.suhyeong.project.product.infrastructure.jpa;

import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryCustomRepository {
    List<Category> findCategories(GetCategoriesCommand command);
    long countCategories(GetCategoriesCommand command);
}
