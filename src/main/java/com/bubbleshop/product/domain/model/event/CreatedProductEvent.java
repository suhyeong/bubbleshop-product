package com.bubbleshop.product.domain.model.event;

import com.bubbleshop.product.domain.model.aggregate.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class CreatedProductEvent {
    private Product product;
}
