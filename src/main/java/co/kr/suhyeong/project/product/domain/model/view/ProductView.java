package co.kr.suhyeong.project.product.domain.model.view;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ProductView {
    private String productCode;
    private String productName;
    private String mainCategoryCode;
    private String subCategoryCode;

    private int price;

    private List<ProductImageView> imageList;

    public ProductView(Product product) {
        this.productCode = product.getProductCode();
        this.productName = product.getProductName();
        this.mainCategoryCode = product.getMainCategoryCode().getCode();
        this.subCategoryCode = product.getMainCategoryCode().getCode();
        this.price = product.getCost();
        this.imageList = new ArrayList<>();
        product.getImages().forEach(image -> imageList.add(new ProductImageView(image.getImageDivCode(), image.getImgPath())));
    }
}
