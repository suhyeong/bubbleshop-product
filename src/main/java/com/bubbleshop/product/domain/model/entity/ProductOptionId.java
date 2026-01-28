package com.bubbleshop.product.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jdk.jfr.Description;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOptionId implements Serializable {
    @Serial
    private static final long serialVersionUID = -2519262076424359625L;

    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("옵션 순번")
    @Column(name = "product_opt_seq")
    private int productOptionSeq;

    public void setProductOptionSeq(int seq) {
        this.productOptionSeq = seq;
    }
}
