package co.kr.suhyeong.project.product.application.internal.queryservice;

import co.kr.suhyeong.project.config.ProductConfig;
import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.GetProductImageCommand;
import co.kr.suhyeong.project.product.domain.command.GetProductListCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.view.ProductImageView;
import co.kr.suhyeong.project.product.domain.model.view.ProductListView;
import co.kr.suhyeong.project.product.domain.model.view.ProductView;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.kr.suhyeong.project.constants.ResponseCode.NON_EXIST_DATA;

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
            throw new ApiException(NON_EXIST_DATA);

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
                .orElseThrow(() -> new ApiException(NON_EXIST_DATA));

        return product.getImages().stream()
                .map(image -> new ProductImageView(image.getImageDivCode(), image.getImgPath()))
                .collect(Collectors.toList());
    }
}
