package co.kr.suhyeong.project.product.domain.model.event;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DeletedProductEvent {
    private Product product;
}
