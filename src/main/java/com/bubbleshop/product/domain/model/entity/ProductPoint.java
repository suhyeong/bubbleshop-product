package com.bubbleshop.product.domain.model.entity;

import com.bubbleshop.product.domain.constant.PointType;
import com.bubbleshop.product.domain.model.aggregate.Product;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_points_master")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductPoint extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 189829536959478489L;

    @EmbeddedId
    private ProductPointId productPointId;

    @Description("적립 포인트 금액")
    @Column(name = "save_points")
    private Integer savePoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    @ToString.Exclude
    private Product product;

    public ProductPoint(String productCode, PointType pointType, int savePoint) {
        this.productPointId = new ProductPointId(productCode, pointType);
        this.savePoints = savePoint;
    }
}
