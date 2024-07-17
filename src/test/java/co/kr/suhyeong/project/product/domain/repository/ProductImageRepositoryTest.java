package co.kr.suhyeong.project.product.domain.repository;

import co.kr.suhyeong.project.config.ProductMockData;
import co.kr.suhyeong.project.config.QueryDslTestConfig;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImageId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static co.kr.suhyeong.project.product.domain.constant.ProductImageCode.FULL_DETAIL_IMAGE;
import static co.kr.suhyeong.project.product.domain.constant.ProductImageCode.THUMBNAIL_IMAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        ProductImage image1 = ProductImage.builder()
                .productImageId(new ProductImageId(productCode, THUMBNAIL_IMAGE))
                .imgPath(path1).build();
        ProductImage image2 = ProductImage.builder()
                .productImageId(new ProductImageId(productCode, FULL_DETAIL_IMAGE))
                .imgPath(path2)
                .build();
        Product product = ProductMockData.createProduct(productCode, List.of(image1, image2));

        // product pk, fk - product image pk 연관관계로
        // product pk 데이터가 없으면 이미지도 조회할 수가 없어 에러가 발생하기 때문에 product 정보를 저장하여 이미지 정보를 조회한다.
        productRepository.save(product);

        //when
        List<ProductImage> actual = productImageRepository.findByProductImageId_ProductCodeAndProductImageId_DivCodeIn(productCode, List.of(THUMBNAIL_IMAGE, FULL_DETAIL_IMAGE));

        //then
        assertAll(
                () -> assertEquals(2, actual.size()),
                () -> assertEquals(productCode, actual.get(0).getProductImageId().getProductCode()),
                () -> assertEquals(productCode, actual.get(1).getProductImageId().getProductCode())
        );

        for (ProductImage image : actual) {
            if(image.isThumbnailImage())
                assertEquals(path1, image.getImgPath());
            else assertEquals(path2, image.getImgPath());
        }
    }
}