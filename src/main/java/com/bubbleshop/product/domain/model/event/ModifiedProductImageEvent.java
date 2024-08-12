package com.bubbleshop.product.domain.model.event;

import com.bubbleshop.product.domain.model.aggregate.Product;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
public class ModifiedProductImageEvent {
    String productCode;
    Map<String, List<String>> images;

    public ModifiedProductImageEvent(Product product, Map<String, List<String>> images) {
        this.productCode = product.getProductCode();
        this.images = images;
    }
}
