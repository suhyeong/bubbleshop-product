package co.kr.suhyeong.project.product.domain.model.view;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImageView {
    private String imageDivCode;
    private String imagePath;
    private String imageFullPath;

    public ProductImageView(String imageDivCode, String imagePath) {
        this.imageDivCode = imageDivCode;
        this.imagePath = imagePath;
    }

    public void applyImageFullPath(String productCode, String imagePath) {
        this.imageFullPath = imagePath + "/" + productCode + "/" + this.imagePath;
    }
}
