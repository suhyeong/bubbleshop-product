package com.bubbleshop.product.domain.model.view;

import com.bubbleshop.product.domain.model.entity.ProductImage;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImageView {
    private int imageSequence;
    private String imageDivCode;
    private String imagePath;
    private String imageFullPath;

    public ProductImageView(ProductImage productImage) {
        this.imageSequence = productImage.getImageSequence();
        this.imageDivCode = productImage.getImageDivCode();
        this.imagePath = productImage.getImgPath();
    }

    public ProductImageView(String imageDivCode, String imagePath) {
        this.imageDivCode = imageDivCode;
        this.imagePath = imagePath;
    }

    public void applyImageFullPath(String productCode, String imagePath) {
        this.imageFullPath = imagePath + "/" + productCode + "/" + this.imagePath;
    }
}
