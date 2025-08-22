package com.bubbleshop.product.domain.model.view;

import com.bubbleshop.product.domain.constant.PointType;
import com.bubbleshop.product.domain.model.entity.ProductPoint;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductPointView {
    private PointType pointType;
    private int savePoint;

    public ProductPointView(ProductPoint point) {
        this.pointType = point.getProductPointId().getPointType();
        this.savePoint = point.getSavePoints();
    }
}
