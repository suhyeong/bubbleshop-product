package co.kr.suhyeong.project.product.domain.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductListView {
    private long count;
    private List<ProductView> productList;
}
