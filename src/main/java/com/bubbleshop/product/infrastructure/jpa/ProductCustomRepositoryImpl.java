package com.bubbleshop.product.infrastructure.jpa;

import com.bubbleshop.product.domain.command.GetProductListCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.model.aggregate.QCategory;
import com.bubbleshop.product.domain.model.view.MainProductView;
import com.bubbleshop.product.domain.model.view.ProductView;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

import static com.bubbleshop.constants.StaticValues.MAIN_PRODUCT_COUNT;
import static com.bubbleshop.product.domain.model.aggregate.QProduct.product;
import static com.bubbleshop.product.domain.model.entity.QProductFeature.productFeature;

public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProductCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ProductCustomRepositoryImpl.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public ProductView findByProductCode(String productCode) {
        QCategory mainCategory = new QCategory("mainCategory");
        QCategory subCategory = new QCategory("subCategory");

        return jpaQueryFactory
                .select(Projections.constructor(ProductView.class, product, mainCategory, subCategory))
                .from(product)
                .join(mainCategory).on(product.mainCategoryCode.eq(mainCategory.code))
                .join(subCategory).on(product.subCategoryCode.eq(subCategory.code))
                .where(product.productCode.eq(productCode))
                .fetchOne();
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

    @Override
    public List<MainProductView> findProductListByOrderCnt() {
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory
                .select(Projections.constructor(MainProductView.class, product))
                .from(product)
                .where(product.displayStartDate.loe(now).and(product.displayEndDate.goe(now)))
                .orderBy(product.orderCount.desc(), product.productCode.asc())
                .limit(MAIN_PRODUCT_COUNT)
                .fetch();
    }

    @Override
    public List<MainProductView> findProductListByOrderDeadlineDate() {
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory
                .select(Projections.constructor(MainProductView.class, product))
                .from(product)
                .where(product.displayStartDate.loe(now).and(product.displayEndDate.goe(now))
                        .and(product.orderDeadlineDate.goe(now)))
                .orderBy(product.orderDeadlineDate.asc(), product.productCode.asc())
                .limit(MAIN_PRODUCT_COUNT)
                .fetch();
    }

    @Override
    public List<MainProductView> findProductListByFeature(FeatureType featureType) {
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory
                .select(Projections.constructor(MainProductView.class, product))
                .from(product)
                .join(productFeature).on(productFeature.productFeatureId.productCode.eq(product.productCode))
                .where(product.displayStartDate.loe(now).and(product.displayEndDate.goe(now))
                        .and(productFeature.productFeatureId.featureType.eq(featureType)))
                .orderBy(product.modifiedDate.desc())
                .limit(MAIN_PRODUCT_COUNT)
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
