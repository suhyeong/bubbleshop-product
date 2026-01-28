package com.bubbleshop.product.domain.command;

import com.bubbleshop.product.domain.constant.ProductImageCode;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class ModifyProductImageCommand {
    private String productCode;
    private List<ProductImage> images;

    /**
     * sequence 가 Null 인 경우 : 새 이미지
     * sequence 가 있는 경우 : 기존 이미지 (수정 없음)
     */
    @Getter
    @Builder
    public static class ProductImage {
        private Long id;
        private ProductImageCode imageDivCode;
        private String path;

        public boolean isNewImage() { return Objects.isNull(this.id); }
    }
}
