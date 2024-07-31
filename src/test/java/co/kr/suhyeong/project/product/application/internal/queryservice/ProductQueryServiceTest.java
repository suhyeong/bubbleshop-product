package co.kr.suhyeong.project.product.application.internal.queryservice;

import co.kr.suhyeong.project.config.ProductMockData;
import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.GetProductImageCommand;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImageId;
import co.kr.suhyeong.project.product.domain.model.view.ProductImageView;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {
    @InjectMocks
    private ProductQueryService productQueryService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("DB 에서 데이터를 정상적으로 조회하여 리스트 정보를 리턴한다.")
    void getProductImages_success() {
        //given
        // 데이터 모킹처리를 진행한다.
        // 해당 테스트 파일에서 수행되는 메소드 내부에 필요한 응답값들을 모킹처리해주면 된다.
        // 해당 서비스 내에서는 레포지토리에서 가져온 값이 모킹처리 되어야 한다.
        ProductImage image1 = ProductImage.builder()
                .productImageId(new ProductImageId("001", ProductImageCode.THUMBNAIL_IMAGE, 1))
                .imgPath("path1")
                .build();

        ProductImage image2 = ProductImage.builder()
                .productImageId(new ProductImageId("001", ProductImageCode.FULL_DETAIL_IMAGE, 2))
                .imgPath("path2")
                .build();

        Product product = ProductMockData.createProduct("001", List.of(image1, image2));

        given(productRepository.findById(any())).willReturn(Optional.of(product));

        //when
        //실제로 수행할 메소드를 호출한다.
        List<ProductImageView> actual =
                productQueryService.getProductImages(GetProductImageCommand.builder().productCode("001").productImageCodeList(List.of()).build());

        ProductImageView expectedImage1 = new ProductImageView(image1.getImageDivCode(), image1.getImgPath());

        //then
        //결과값을 확인한다.
        assertThat(actual.size()).isEqualTo(2);
        //똑같은 의미
        assertEquals(actual.size(), 2);
        assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(expectedImage1);
    }

    @Test
    @DisplayName("DB 조회시 DB 에서 알 수 없는 에러가 발생한다.")
    void getProductImages_db_error() {
        //given
        given(productRepository.findById(any()))
                .willThrow(new MockitoException("DB 에러가 났다고 가정해볼까요?"));

        //when, then
        assertThrows(Exception.class, () -> productQueryService.getProductImages(GetProductImageCommand.builder().productCode("001").productImageCodeList(List.of()).build()));
    }
}