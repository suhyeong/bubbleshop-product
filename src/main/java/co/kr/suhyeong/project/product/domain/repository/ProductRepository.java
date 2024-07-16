package co.kr.suhyeong.project.product.domain.repository;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.infrastructure.jpa.ProductCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String>, ProductCustomRepository {
    int countByMainCategoryCodeAndSubCategoryCode(String mainCategoryCode, String subCategoryCode);
}
