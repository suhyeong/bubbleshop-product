package co.kr.suhyeong.project.product.domain.model.entity;

import jdk.jfr.Description;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class ProductImageId implements Serializable {
    @Description("상품 코드")
    private String productCode;

    @Description("상품 이미지 순번")
    private int productImgSeq;
}
