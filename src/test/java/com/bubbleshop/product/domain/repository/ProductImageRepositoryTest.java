package com.bubbleshop.product.domain.repository;

import com.bubbleshop.config.ProductMockData;
import com.bubbleshop.config.QueryDslTestConfig;
import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.entity.ProductImage;
import com.bubbleshop.product.domain.model.entity.ProductImageId;
import com.bubbleshop.product.domain.constant.ProductImageCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QueryDslTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductImageRepositoryTest {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 코드와 이미지 타입으로 이미지를 조회한다.")
    void findByProductImageId_ProductCodeAndProductImageId_DivCodeIn() {
        //given
        String productCode = "001";
        String path1 = "썸네일 Path 1";
        String path2 = "상세 Path 2";

        Product product = ProductMockData.createProduct(productCode, new ArrayList<>());

        ProductImage image1 = new ProductImage(product, ProductImageCode.THUMBNAIL_IMAGE, path1);
        ProductImage image2 = new ProductImage(product, ProductImageCode.FULL_DETAIL_IMAGE, path2);

        product.getImages().add(image1);
        product.getImages().add(image2);

        // product pk, fk - product image pk 연관관계로
        // product pk 데이터가 없으면 이미지도 조회할 수가 없어 에러가 발생하기 때문에 product 정보를 저장하여 이미지 정보를 조회한다.
        productRepository.save(product);

        //when
        List<ProductImage> actual = productImageRepository.findByProduct_ProductCodeAndDivCodeIn(productCode, List.of(ProductImageCode.THUMBNAIL_IMAGE, ProductImageCode.FULL_DETAIL_IMAGE));

        //then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertNotNull(actual.get(0).getId()),
                () -> assertNotNull(actual.get(1).getId())
        );

        for (ProductImage image : actual) {
            if(image.isThumbnailImage())
                assertEquals(path1, image.getImgPath());
            else assertEquals(path2, image.getImgPath());
        }
    }
}