package co.kr.suhyeong.project.product.domain.model.entity;

import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.converter.ProductImageCodeConverter;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class ProductImageId implements Serializable {
    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("이미지 구분 코드")
    @Column(name = "img_div_code")
    @Convert(converter = ProductImageCodeConverter.class)
    private ProductImageCode divCode;

    public boolean isThumbnailImage() {
        return this.divCode.equals(ProductImageCode.THUMBNAIL_IMAGE);
    }

    public boolean isDetailImage() {
        return this.divCode.equals(ProductImageCode.FULL_DETAIL_IMAGE);
    }
}
