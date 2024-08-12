package com.bubbleshop.product.application.internal.commandservice;

import com.bubbleshop.config.ProductMockData;
import com.bubbleshop.config.S3BucketTestConfig;
import com.bubbleshop.product.domain.command.ModifyProductImageCommand;
import com.bubbleshop.product.domain.constant.ProductImageCode;
import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.entity.ProductImage;
import com.bubbleshop.product.domain.model.entity.ProductImageId;
import com.bubbleshop.product.domain.repository.ProductRepository;
import com.bubbleshop.product.domain.service.S3BucketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@Import(S3BucketTestConfig.class)
class ProductImageCommandServiceTest {
    @InjectMocks
    private ProductImageCommandService productImageCommandService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private S3BucketService s3BucketService;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("이미지 정보를 수정한다. | 기존 이미지 전체 삭제할 경우")
    void modifyProductImages_all_img_delete() {
        //given
        ProductImage image1 = ProductImage.builder()
                .productImageId(new ProductImageId("001", ProductImageCode.THUMBNAIL_IMAGE, 1))
                .imgPath("path1")
                .build();
        Product product = ProductMockData.createProduct("001", new ArrayList<>(List.of(image1)));
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        ModifyProductImageCommand command = ModifyProductImageCommand.builder()
                .productCode("001")
                .build();

        //when
        productImageCommandService.modifyProductImages(command);

        //then
        assertThat(product.getImages().size()).isZero();
    }

    @Test
    @DisplayName("이미지 정보를 수정한다. | 기존 이미지가 없었을 경우")
    void modifyProductImages_all_img_save() {
        //given
        Product product = ProductMockData.createProduct("001", new ArrayList<>());
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        List<ModifyProductImageCommand.ProductImage> images = new ArrayList<>();
        images.add(ModifyProductImageCommand.ProductImage.builder().imageDivCode(ProductImageCode.THUMBNAIL_IMAGE).path("path1").build());
        images.add(ModifyProductImageCommand.ProductImage.builder().imageDivCode(ProductImageCode.FULL_DETAIL_IMAGE).path("path2").build());
        ModifyProductImageCommand command = ModifyProductImageCommand.builder()
                .productCode("001")
                .images(images)
                .build();

        //when
        productImageCommandService.modifyProductImages(command);

        //then
        assertThat(product.getImages().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("이미지 정보를 수정한다. | 기존 이미지가 있을 경우")
    void modifyProductImages_modify_image() {
        //given
        ProductImage image1 = ProductImage.builder()
                .productImageId(new ProductImageId("001", ProductImageCode.THUMBNAIL_IMAGE, 1))
                .imgPath("path1")
                .build();
        Product product = ProductMockData.createProduct("001", new ArrayList<>(List.of(image1)));
        given(productRepository.findById(any())).willReturn(Optional.of(product));

        List<ModifyProductImageCommand.ProductImage> images = new ArrayList<>();
        images.add(ModifyProductImageCommand.ProductImage.builder().imageDivCode(ProductImageCode.THUMBNAIL_IMAGE).path("path2").build());
        images.add(ModifyProductImageCommand.ProductImage.builder().imageDivCode(ProductImageCode.FULL_DETAIL_IMAGE).path("path1").build());
        images.add(ModifyProductImageCommand.ProductImage.builder().imageDivCode(ProductImageCode.FULL_DETAIL_IMAGE).path("path3").build());
        images.add(ModifyProductImageCommand.ProductImage.builder().imageDivCode(ProductImageCode.FULL_DETAIL_IMAGE).path("path4").build());
        ModifyProductImageCommand command = ModifyProductImageCommand.builder()
                .productCode("001")
                .images(images)
                .build();

        //when
        productImageCommandService.modifyProductImages(command);

        //then
        assertThat(product.getImages().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("상품 기본 정보를 수정한다.")
    void modifyProduct_success() {
        //given

        //when

        //then
    }
}