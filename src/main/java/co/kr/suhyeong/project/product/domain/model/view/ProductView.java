package co.kr.suhyeong.project.product.domain.model.view;

import co.kr.suhyeong.project.product.domain.constant.FeatureType;
import co.kr.suhyeong.project.product.domain.model.aggregate.Category;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.util.DateTimeUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static co.kr.suhyeong.project.util.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DOT;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductView {
    private String productCode;
    private String productName;
    private String productEngName;
    private String createdAt;

    private String mainCategoryCode;
    private String mainCategoryName;
    private String subCategoryCode;
    private String subCategoryName;

    private int price;

    private List<ProductImageView> imageList;
    private Set<FeatureType> featureTypes;
    private List<ProductOptionView> options;

    @QueryProjection
    public ProductView(Product product, Category mainCategory, Category subCategory) {
        this.productCode = product.getProductCode();
        this.productName = product.getProductName();
        this.productEngName = product.getProductEngName();
        this.createdAt = DateTimeUtils.convertDateTimeToString(DATE_FORMAT_YYYY_MM_DD_DOT, product.getCreatedDate());
        this.mainCategoryCode = product.getMainCategoryCode();
        this.mainCategoryName = mainCategory.getName();
        this.subCategoryCode = product.getSubCategoryCode();
        this.subCategoryName = subCategory.getName();
        this.price = product.getCost();
        this.featureTypes = product.getFeatureTypes();
        this.imageList = new ArrayList<>();
        product.getImages().forEach(image -> imageList.add(new ProductImageView(image.getImageDivCode(), image.getImgPath())));
        this.options = new ArrayList<>();
        product.getOptions().forEach(option -> options.add(new ProductOptionView(option)));
    }
}
