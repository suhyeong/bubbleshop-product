package com.bubbleshop.product.domain.model.view;

import com.bubbleshop.product.domain.constant.PointType;
import com.bubbleshop.product.domain.model.aggregate.Category;
import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.util.DateTimeUtils;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bubbleshop.util.DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_DOT;

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
    private int discountRate;
    private Boolean isSale;

    private List<ProductImageView> imageList;
    private List<ProductFeatureView> featureTypes;
    private List<ProductOptionView> options;
    private List<ProductPointView> points;

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
        this.discountRate = product.getDiscount_rate();
        this.isSale = product.isSale();

        this.featureTypes = new ArrayList<>();
        product.getFeatures().forEach(feature -> featureTypes.add(new ProductFeatureView(feature)));

        this.imageList = new ArrayList<>();
        product.getImages().forEach(image -> imageList.add(new ProductImageView(image)));

        this.options = new ArrayList<>();
        product.getOptions().forEach(option -> options.add(new ProductOptionView(option)));

        this.points = new ArrayList<>();
        product.getPoints().forEach(point -> points.add(new ProductPointView(point)));
    }

    public ProductView(Product product) {
        this.productCode = product.getProductCode();
        this.productName = product.getProductName();
    }

    public void applyImagePath(String imagePath) {
        this.imageList.forEach(image -> image.applyImageFullPath(this.productCode, imagePath));
    }
}
