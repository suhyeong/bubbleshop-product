package co.kr.suhyeong.project.config;

import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;
import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;

import java.util.List;

public class ProductMockData {
    public static Product createProduct(List<ProductImage> images) {
        return Product.builder()
                .productCode("00001")
                .productName("테스트")
                .mainCategoryCode(MainCategoryCode.MAIN_CATEGORY_01)
                .subCategoryCode(SubCategoryCode.SUB_CATEGORY_01)
                .cost(100000)
                .images(images)
                .build();
    }
}
