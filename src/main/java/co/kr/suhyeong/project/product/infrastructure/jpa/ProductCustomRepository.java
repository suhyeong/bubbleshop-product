package co.kr.suhyeong.project.product.infrastructure.jpa;

import co.kr.suhyeong.project.product.domain.command.GetProductListCommand;
import co.kr.suhyeong.project.product.domain.model.view.ProductView;

import java.util.List;

public interface ProductCustomRepository {
    long countByProductListWithPagination(GetProductListCommand command);
    List<ProductView> findProductListWithPagination(GetProductListCommand command);
}
