package co.kr.suhyeong.project.product.domain.model.aggregate;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.constant.FeatureType;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.converter.ProductFeaturesTypeConverter;
import co.kr.suhyeong.project.product.domain.model.converter.YOrNToBooleanConverter;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.model.entity.ProductOption;
import co.kr.suhyeong.project.product.domain.model.entity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Product extends TimeEntity implements Serializable {

    @Id
    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("상품 이름")
    @Column(name = "product_nm")
    private String productName;

    @Description("상품 영문명")
    @Column(name = "product_eng_nm")
    private String productEngName;

    @Description("상품 메인 카테고리 코드")
    @Column(name = "main_cate_code")
    private String mainCategoryCode;

    @Description("상품 서브 카테고리 코드")
    @Column(name = "sub_cate_code")
    private String subCategoryCode;

    @Description("상품 원가")
    @Column(name = "cost")
    private int cost;

    @Description("할인율")
    @Column(name = "disc_rate")
    private int discount_rate;

    @Description("판매 여부")
    @Convert(converter = YOrNToBooleanConverter.class)
    @Column(name = "sale_yn")
    private boolean isSale;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductOption> options = new ArrayList<>();

    @Description("상품 태그(특징)")
    @Column(name = "product_features")
    @Convert(converter = ProductFeaturesTypeConverter.class)
    private Set<FeatureType> featureTypes;

    public Product(CreateProductCommand command, int sequence) {
        this.productCode = command.getMainCategoryCode() + command.getSubCategoryCode() + String.format("%05d", sequence);
        this.productName = command.getName();
        this.productEngName = command.getEngName();
        this.mainCategoryCode = command.getMainCategoryCode();
        this.subCategoryCode = command.getSubCategoryCode();
        this.cost = command.getPrice();
        this.isSale = false;
        this.featureTypes = command.getFeatureTypes();
        this.createProductImages(command);
        this.createProductOptions(command.getOptionName(), command.getDefaultOptionName());
    }

    private void createProductImages(CreateProductCommand command) {
        this.images = new ArrayList<>();
        if(command.isThumbnailImageExist())
            this.images.add(new ProductImage(this, ProductImageCode.THUMBNAIL_IMAGE, command.getThumbnailImageName(), this.images.size() + 1));
        if(command.isDetailImageExist()) {
            command.getDetailImageName().forEach(name -> {
                this.images.add(new ProductImage(this, ProductImageCode.FULL_DETAIL_IMAGE, name, this.images.size() + 1));
            });
        }
    }

    private void createProductOptions(Set<String> productOptions, String defaultOption) {
        if(Objects.nonNull(productOptions) && !productOptions.isEmpty()) {
            productOptions.forEach(option -> {
                boolean isDefaultOption = defaultOption.equals(option);
                this.options.add(new ProductOption(this.productCode, this.options.size() + 1, option, isDefaultOption));
            });
        }
    }

    public void modifyProduct(ModifyProductCommand command) {
        this.productName = command.getName();
        this.cost = command.getPrice();
        this.discount_rate = command.getDiscount();
        this.isSale = command.isSale();
        this.modifyProductImages(command);
        this.modifyProductOptions(command.getOptionName(), command.getDefaultOptionName());
    }

    // TODO 수정
    private void modifyProductImages(ModifyProductCommand command) {
//        if(Objects.nonNull(this.images) && !this.images.isEmpty()) {
//            this.images.forEach(item -> item.modifyImagePath(command));
//        }
    }

    private boolean isOptionExist() {
        return Objects.nonNull(this.options) && !this.options.isEmpty();
    }

    private void modifyProductOptions(Set<String> newProductOptions, String defaultOption) {
        // 새 옵션 정보가 없을 경우
        if (Objects.isNull(newProductOptions) || newProductOptions.isEmpty()) {
            //기존 옵션이 존재할 경우만 clear
            if(this.isOptionExist())
                this.options.clear();
        }
        // 새 옵션 정보가 있을 경우
        else {
            // 기존 옵션이 존재할 경우
            if(this.isOptionExist()) {
                // 기존 옵션 중 새 옵션에 없는 옵션일 경우 옵션 리스트에서 삭제
                this.options.removeIf(option -> !newProductOptions.contains(option.getOptionName()));

                newProductOptions.forEach(option -> {
                    boolean isDefaultOption = defaultOption.equals(option);

                    //기존에 이미 존재하는 옵션이면 디폴트 여부만 수정, 존재하지 않다면 새 옵션 추가
                    this.options.stream().filter(originOption -> originOption.getOptionName().equals(option)).findAny()
                            .ifPresentOrElse(
                                    sameOption -> sameOption.applyDefaultOption(isDefaultOption),
                                    () -> this.options.add(new ProductOption(this.productCode, this.options.size() + 1, option, isDefaultOption)));
                });
            }
            // 기존 옵션이 존재하지 않을 경우
            else {
                this.createProductOptions(newProductOptions, defaultOption);
            }
        }
    }

    public boolean isSameCategory(String newMainCategoryCode, String newSubCategoryCode) {
        return this.mainCategoryCode.equals(newMainCategoryCode) && this.subCategoryCode.equals(newSubCategoryCode);
    }
}
