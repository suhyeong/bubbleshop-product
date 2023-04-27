package co.kr.suhyeong.project.product.application.internal.queryservice;

import co.kr.suhyeong.project.constants.ApiException;
import co.kr.suhyeong.project.product.domain.command.GetProductImageCommand;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImageId;
import co.kr.suhyeong.project.product.domain.repository.ProductImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductImageQueryServiceTest {
    @InjectMocks
    private ProductImageQueryService productImageQueryService;

    @Mock private ProductImageRepository productImageRepository;

    @Test
    @DisplayName("DB 에서 데이터를 정상적으로 조회하여 리스트 정보를 리턴한다.")
    void getProductImages_success() {
        //given
        // 데이터 모킹처리를 진행한다.
        // 해당 테스트 파일에서 수행되는 메소드 내부에 필요한 응답값들을 모킹처리해주면 된다.
        // 해당 서비스 내에서는 레포지토리에서 가져온 값이 모킹처리 되어야 한다.
        ProductImage image1 = new ProductImage(new ProductImageId("001", 1), ProductImageCode.THUMBNAIL_IMAGE, "path1");
        ProductImage image2 = new ProductImage(new ProductImageId("001", 2), ProductImageCode.THUMBNAIL_IMAGE, "path2");
        given(productImageRepository.findByProductImageId_ProductCodeAndAndDivCodeIn(anyString(), anyList())).willReturn(List.of(image1, image2));
        
        //when
        //실제로 수행할 메소드를 호출한다.
        List<ProductImage> actual = productImageQueryService.getProductImages(GetProductImageCommand.builder().productCode("001").productImageCodeList(List.of()).build());
;
        //then
        //결과값을 확인한다.
        assertThat(actual.size()).isEqualTo(2);
        //똑같은 의미
        assertEquals(actual.size(), 2);
        assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(image1);
    }

    @Test
    @DisplayName("DB 에서 데이터 조회시 데이터가 없어 에러가 발생한다.")
    void getProductImages_no_data() {
        //given
        given(productImageRepository.findByProductImageId_ProductCodeAndAndDivCodeIn(anyString(), anyList())).willReturn(List.of());

        //when
        Exception exception = assertThrows(Exception.class, () -> productImageQueryService.getProductImages(GetProductImageCommand.builder().productCode("001").productImageCodeList(List.of()).build()));

        //then
        assertThat(exception).isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("DB 조회시 DB 에서 알 수 없는 에러가 발생한다.")
    void getProductImages_db_error() {
        //given
        given(productImageRepository.findByProductImageId_ProductCodeAndAndDivCodeIn(anyString(), anyList()))
                .willThrow(new MockitoException("DB 에러가 났다고 가정해볼까요?"));

        //when
        Exception exception = assertThrows(Exception.class, () -> productImageQueryService.getProductImages(GetProductImageCommand.builder().productCode("001").productImageCodeList(List.of()).build()));

        //then
        assertThat(exception).isInstanceOf(ApiException.class);
    }

}