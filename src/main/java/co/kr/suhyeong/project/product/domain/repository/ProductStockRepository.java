package co.kr.suhyeong.project.product.domain.repository;

import co.kr.suhyeong.project.product.domain.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductStockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findByProductCode(String productCode);
}
