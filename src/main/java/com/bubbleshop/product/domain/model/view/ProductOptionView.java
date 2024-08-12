package com.bubbleshop.product.domain.model.view;

import com.bubbleshop.product.domain.model.entity.ProductOption;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOptionView {
    private int optionSequence;
    private String optionName;
    private Boolean isDefaultOption;
    private int stock;

    public ProductOptionView(ProductOption option) {
        this.optionSequence = option.getProductOptionId().getProductOptionSeq();
        this.optionName = option.getOptionName();
        this.isDefaultOption = option.isDefaultOption();
        this.stock = option.getStock().getCount();
    }
}
