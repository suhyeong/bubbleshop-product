package co.kr.suhyeong.project.product.infrastructure.jpa;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCustomRepository {
    List<Product> findProductListWithPagination(Pageable pageable);
}
