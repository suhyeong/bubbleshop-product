package co.kr.suhyeong.project.product.domain.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ProductImageView {
    private String imageDivCode;
    private String imagePath;
}
