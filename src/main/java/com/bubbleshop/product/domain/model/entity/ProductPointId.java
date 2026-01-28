package com.bubbleshop.product.domain.model.entity;

import com.bubbleshop.product.domain.constant.PointType;
import com.bubbleshop.product.domain.model.converter.ProductPointTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
public class ProductPointId implements Serializable {
    @Serial
    private static final long serialVersionUID = -7449202244134966655L;

    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("포인트 코드")
    @Column(name = "point_code")
    @Convert(converter = ProductPointTypeConverter.class)
    private PointType pointType;

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }
}
