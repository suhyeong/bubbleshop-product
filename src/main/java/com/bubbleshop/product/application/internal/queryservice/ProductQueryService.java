package com.bubbleshop.product.application.internal.queryservice;

import com.bubbleshop.config.ProductConfig;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.product.domain.command.GetProductImageCommand;
import com.bubbleshop.product.domain.command.GetProductListCommand;
import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.view.ProductImageView;
import com.bubbleshop.product.domain.model.view.ProductListView;
import com.bubbleshop.product.domain.model.view.ProductView;
import com.bubbleshop.product.domain.repository.ProductRepository;
import com.bubbleshop.constants.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    public List<ProductImageView> getProductImages(GetProductImageCommand command) {
        Product product = productRepository.findById(command.getProductCode())
                .orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));

        return product.getImages().stream()
                .map(image -> new ProductImageView(image.getImageDivCode(), image.getImgPath()))
                .collect(Collectors.toList());
    }
}
