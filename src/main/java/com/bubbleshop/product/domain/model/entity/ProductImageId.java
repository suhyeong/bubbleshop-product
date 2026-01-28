package com.bubbleshop.product.domain.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jdk.jfr.Description;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class ProductImageId implements Serializable {
    @Serial
    private static final long serialVersionUID = 9030586013558451264L;

    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("상품 이미지 순번")
    @Column(name = "product_img_seq")
    private int imageSequence;

    public void setProductImage(int sequence) {
        this.imageSequence = sequence;
    }
}
