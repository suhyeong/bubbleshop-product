package com.bubbleshop.product.application.internal.queryservice;

import com.bubbleshop.config.ProductConfig;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.product.domain.command.GetProductImageCommand;
import com.bubbleshop.product.domain.command.GetProductListCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.constant.MainProductType;
import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.view.MainProductView;
import com.bubbleshop.product.domain.model.view.ProductImageView;
import com.bubbleshop.product.domain.model.view.ProductListView;
import com.bubbleshop.product.domain.model.view.ProductView;
import com.bubbleshop.product.domain.repository.ProductRepository;
import com.bubbleshop.constants.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {
    private final ProductRepository productRepository;
    private final ProductConfig productConfig;

    @Cacheable(cacheNames = StaticValues.RedisKey.PRODUCT_KEY, key = "#productCode")
    public ProductView getProduct(String productCode) {
        ProductView product = productRepository.findByProductCode(productCode);
        if(Objects.isNull(product))
            throw new ApiException(ResponseCode.NON_EXIST_DATA);

        product.applyImagePath(productConfig.getImageUrl());
        return product;
    }

    public ProductListView getProductList(GetProductListCommand command) {
        long count = productRepository.countByProductListWithPagination(command);
        List<ProductView> list = productRepository.findProductListWithPagination(command);
        return new ProductListView(count, list);
    }

    public Map<String, ProductView> getProductList(List<String> productIds) {
        List<Product> products = productRepository.findProductsByProductCodeIn(productIds);

        if(products.isEmpty())
            return null;

        List<ProductView> productViews = products.stream().map(ProductView::new).collect(Collectors.toList());
        return productViews.stream().collect(Collectors.toMap(ProductView::getProductCode, Function.identity()));
    }


    public List<ProductImageView> getProductImages(GetProductImageCommand command) {
        Product product = productRepository.findById(command.getProductCode())
                .orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));

        return product.getImages().stream()
                .map(image -> new ProductImageView(image.getImageDivCode(), image.getImgPath()))
                .collect(Collectors.toList());
    }

    /**
     * 메인 페이지의 상품 리스트 조회 (12건 제한)
     *
     * 신상순 : 상품 테이블 - 상품 태그 테이블 기준으로 "신상품" 태그가 존재하는 상품을 order by DESC 하여 리턴
     * 재입고 : 상품 테이블 - 상품 태그 테이블 기준으로 "재입고" 태그가 존재하는 상품을 order by DESC 하여 리턴
     * 예약마감순 : 상품 테이블의 주문 마감일 컬럼 기준으로 order by ASC 하여 리턴
     * 인기순 : 상품 테이블의 주문 수 기준으로 order by DESC 하여 주문 많은 건 순으로 리턴
     * @param mainProductType
     * @return
     */
    @Cacheable(cacheNames = StaticValues.RedisKey.PRODUCT_KEY, key = "#mainProductType")
    public List<MainProductView> getMainProductList(MainProductType mainProductType) {
        List<MainProductView> productViews = new ArrayList<>();

        switch (mainProductType) {
            case POPULARITY -> productViews = productRepository.findProductListByOrderCnt();
            case RESERVE_CLOSE -> productViews = productRepository.findProductListByOrderDeadlineDate();
            case NEW -> productViews = productRepository.findProductListByFeature(FeatureType.NEW);
            case STOCK_UP -> productViews = productRepository.findProductListByFeature(FeatureType.STOCK_UP);
            // do nothing
            default -> { return productViews; }
        }

        // 썸네일 이미지 Full URL 세팅 & 태그 정렬
        for(MainProductView view : productViews) {
            if(ObjectUtils.isNotEmpty(view.getThumbnailImage())) {
                view.getThumbnailImage().applyImageFullPath(view.getProductCode(), productConfig.getImageUrl());
            }
            view.sortFeatureTypes();
        }

        return productViews;
    }
}
