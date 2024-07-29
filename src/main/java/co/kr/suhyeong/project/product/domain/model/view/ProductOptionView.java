package co.kr.suhyeong.project.product.domain.model.view;

import co.kr.suhyeong.project.product.domain.model.entity.ProductOption;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOptionView {
    private int optionSequence;
    private String optionName;
    private boolean isDefaultOption;
    private int stock;

    public ProductOptionView(ProductOption option) {
        this.optionSequence = option.getProductOptionId().getProductOptionSeq();
        this.optionName = option.getOptionName();
        this.isDefaultOption = option.isDefaultOption();
        this.stock = option.getStock().getCount();
    }
}
