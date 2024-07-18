package co.kr.suhyeong.project.product.infrastructure.jpa;

import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import co.kr.suhyeong.project.product.domain.model.aggregate.Category;
import co.kr.suhyeong.project.product.domain.model.aggregate.QCategory;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

import static co.kr.suhyeong.project.product.domain.model.aggregate.QCategory.category;

public class CategoryCustomRepositoryImpl extends QuerydslRepositorySupport implements CategoryCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public CategoryCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(CategoryCustomRepositoryImpl.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Category> findCategories(GetCategoriesCommand command) {
        if(command.isNeedToPaging()) {
            return jpaQueryFactory
                    .selectFrom(category)
                    .where(this.whereCategoryType(command.isCategoryTypeExist(), command.getCategoryType()))
                    .offset(command.getPaging().getOffset())
                    .limit(command.getPaging().getPageSize())
                    .fetch();
        }
        else return jpaQueryFactory
                .selectFrom(category)
                .where(this.whereCategoryType(command.isCategoryTypeExist(), command.getCategoryType()))
                .fetch();
    }

    private Predicate whereCategoryType(boolean isCategoryTypeExist, CategoryType categoryType) {
        return isCategoryTypeExist ? category.categoryType.eq(categoryType) : null;
    }

    @Override
    public List<Category> findCategoriesWithPagination(Pageable pageable) {
        return null;
    }
}
