package co.kr.suhyeong.project.product.domain.repository;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByProductCode(String productCode);
    Optional<Product> findByMainCategoryCodeAndSubCategoryCode(String mainCategoryCode, String subCategoryCode);
}
