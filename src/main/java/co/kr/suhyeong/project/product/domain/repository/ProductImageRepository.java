package co.kr.suhyeong.project.product.domain.repository;

import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, ProductImageId> {
    List<ProductImage> findByProductImageId_ProductCodeAndProductImageId_DivCodeIn(String productCode, List<ProductImageCode> imageCodes);
    List<ProductImage> findByProductImageId_ProductCode(String productCode);
}
