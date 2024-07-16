package co.kr.suhyeong.project.config;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;

import java.util.List;

public class ProductMockData {
    public static Product createProduct(String code, List<ProductImage> images) {
        return Product.builder()
                .productCode(code)
                .productName("테스트")
                .mainCategoryCode("01")
                .subCategoryCode("01")
                .cost(100000)
                .images(images)
                .build();
    }
}
