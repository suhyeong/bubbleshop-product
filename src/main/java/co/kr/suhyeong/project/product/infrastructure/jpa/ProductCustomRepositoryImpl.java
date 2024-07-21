package co.kr.suhyeong.project.product.infrastructure.jpa;

import co.kr.suhyeong.project.product.domain.command.GetProductListCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.QCategory;
import co.kr.suhyeong.project.product.domain.model.view.ProductView;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static co.kr.suhyeong.project.product.domain.model.aggregate.QProduct.product;

public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProductCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ProductCustomRepositoryImpl.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public long countByProductListWithPagination(GetProductListCommand command) {
        QCategory mainCategory = new QCategory("mainCategory");
        QCategory subCategory = new QCategory("subCategory");

        return jpaQueryFactory
                .select(product.productCode)
                .from(product)
                .join(mainCategory).on(product.mainCategoryCode.eq(mainCategory.code))
                .join(subCategory).on(product.subCategoryCode.eq(subCategory.code))
                .where(this.whereProductList(command))
                .fetch().size();
    }

    @Override
    public List<ProductView> findProductListWithPagination(GetProductListCommand command) {
        QCategory mainCategory = new QCategory("mainCategory");
        QCategory subCategory = new QCategory("subCategory");

        return jpaQueryFactory
                .select(Projections.constructor(ProductView.class, product, mainCategory, subCategory))
                .from(product)
                .join(mainCategory).on(product.mainCategoryCode.eq(mainCategory.code))
                .join(subCategory).on(product.subCategoryCode.eq(subCategory.code))
                .where(this.whereProductList(command))
                .limit(command.getPageable().getPageSize())
                .offset(command.getPageable().getOffset())
                .fetch();
    }

    private BooleanBuilder whereProductList(GetProductListCommand command) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(command.getProductCode())) {
            booleanBuilder.and(product.productCode.eq(command.getProductCode()));
        }

        if(StringUtils.isNotBlank(command.getProductName())) {
            if(command.isNameContains())
                booleanBuilder.and(product.productName.contains(command.getProductName()));
            else
                booleanBuilder.and(product.productName.eq(command.getProductName()));
        }

        if(StringUtils.isNotBlank(command.getMainCategoryCode())) {
            booleanBuilder.and(product.mainCategoryCode.eq(command.getMainCategoryCode()));
        }

        if(StringUtils.isNotBlank(command.getSubCategoryCode())) {
            booleanBuilder.and(product.subCategoryCode.eq(command.getSubCategoryCode()));
        }

        return booleanBuilder;
    }
}
