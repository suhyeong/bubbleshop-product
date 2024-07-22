package co.kr.suhyeong.project.product.infrastructure.jpa;

import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import co.kr.suhyeong.project.product.domain.model.aggregate.Category;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static co.kr.suhyeong.project.product.domain.model.aggregate.QCategory.category;
import static co.kr.suhyeong.project.product.domain.model.aggregate.QProduct.product;

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
                    .where(this.whereCategoryList(command))
                    .offset(command.getPaging().getOffset())
                    .limit(command.getPaging().getPageSize())
                    .fetch();
        }
        else return jpaQueryFactory
                .selectFrom(category)
                .where(this.whereCategoryList(command))
                .fetch();
    }

    @Override
    public long countCategories(GetCategoriesCommand command) {
        return jpaQueryFactory
                .select(category.code)
                .from(category)
                .where(this.whereCategoryList(command))
                .fetch().size();
    }

    private BooleanBuilder whereCategoryList(GetCategoriesCommand command) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(command.getCategoryCode())) {
            booleanBuilder.and(category.code.eq(command.getCategoryCode()));
        }

        if(StringUtils.isNotBlank(command.getCategoryName())) {
            if(command.isCategoryNameContains())
                booleanBuilder.and(category.name.contains(command.getCategoryName()));
            else
                booleanBuilder.and(category.name.eq(command.getCategoryName()));
        }

        if(command.isCategoryTypeExist()) {
            booleanBuilder.and(category.categoryType.eq(command.getCategoryType()));
        }

        return booleanBuilder;
    }
}
