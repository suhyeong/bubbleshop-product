package co.kr.suhyeong.project.product.domain.model.valueobject;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreatedProductEvent {
    private Product product;

    public CreatedProductEvent(Product product) {
        this.product = product;
    }
}
