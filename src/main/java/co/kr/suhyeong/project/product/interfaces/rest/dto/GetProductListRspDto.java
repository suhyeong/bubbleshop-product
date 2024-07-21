package co.kr.suhyeong.project.product.interfaces.rest.dto;

import co.kr.suhyeong.project.product.domain.model.view.ProductView;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProductListRspDto {
    private long totalCount;
    private List<ProductView> productList;
}
