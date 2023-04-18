package co.kr.suhyeong.project.product.domain.model.entity;

import jdk.jfr.Description;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class ProductImageId implements Serializable {
    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("상품 이미지 순번")
    @Column(name = "product_img_seq")
    private int productImgSeq;
}
