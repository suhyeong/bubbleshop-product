package co.kr.suhyeong.project.product.domain.model.entity;

import jdk.jfr.Description;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOptionId implements Serializable {
    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("옵션 순번")
    @Column(name = "product_opt_seq")
    private int productOptionSeq;
}
