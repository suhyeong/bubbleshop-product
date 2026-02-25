package com.bubbleshop.product.infrastructure.jpa;

import com.bubbleshop.product.domain.command.GetProductListCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.model.view.MainProductView;
import com.bubbleshop.product.domain.model.view.ProductView;

import java.util.List;

public interface ProductCustomRepository {
    ProductView findByProductCode(String productCode);
    long countByProductListWithPagination(GetProductListCommand command);
    List<ProductView> findProductListWithPagination(GetProductListCommand command);
    List<MainProductView> findProductListByOrderCnt();
    List<MainProductView> findProductListByOrderDeadlineDate();
    List<MainProductView> findProductListByFeature(FeatureType featureType);
}
