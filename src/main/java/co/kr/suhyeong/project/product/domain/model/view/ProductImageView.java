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
}
