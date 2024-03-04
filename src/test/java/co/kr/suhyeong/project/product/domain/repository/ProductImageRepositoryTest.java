package co.kr.suhyeong.project.product.domain.repository;

import co.kr.suhyeong.project.config.QueryDslTestConfig;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QueryDslTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductImageRepositoryTest {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Test
    @DisplayName("상품 코드와 이미지 타입으로 이미지를 조회한다.")
    void findByProductImageId_ProductCodeAndProductImageId_DivCodeIn() {
        //given
        String productCode = "001";
        String path1 = "썸네일 Path 1";
        String path2 = "상세 Path 2";
        ProductImage image1 = new ProductImage(new ProductImageId(productCode, THUMBNAIL_IMAGE), path1);
        ProductImage image2 = new ProductImage(new ProductImageId(productCode, FULL_DETAIL_IMAGE), path2);
        productImageRepository.save(image1);
        productImageRepository.save(image2);

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