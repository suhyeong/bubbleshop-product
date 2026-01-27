package com.bubbleshop.product.domain.model.aggregate;

import com.bubbleshop.product.domain.command.CreateProductCommand;
import com.bubbleshop.product.domain.command.ModifyProductCommand;
import com.bubbleshop.product.domain.command.ModifyProductImageCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.constant.PointType;
import com.bubbleshop.product.domain.constant.ProductImageCode;
import com.bubbleshop.product.domain.model.converter.YOrNToBooleanConverter;
import com.bubbleshop.product.domain.model.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.bubbleshop.constants.StaticValues.ImageStatus;

@Entity
@Table(name = "product_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Product extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -4203406260459802808L;

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
    private int discountRate;

    @Description("판매 여부")
    @Convert(converter = YOrNToBooleanConverter.class)
    @Column(name = "sale_yn")
    private boolean isSale;

    @Description("전시 시작일")
    @Column(name = "display_start_dt")
    private LocalDateTime displayStartDate;

    @Description("전시 종료일")
    @Column(name = "display_end_dt")
    private LocalDateTime displayEndDate;

    @OneToMany(mappedBy = "product", targetEntity = ProductImage.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductOption> options = new ArrayList<>();

    @OneToMany(mappedBy = "product", targetEntity = ProductFeature.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductFeature> features = new ArrayList<>();

    @OneToMany(mappedBy = "product", targetEntity = ProductPoint.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductPoint> points = new ArrayList<>();

    public Product(CreateProductCommand command, int sequence) {
        this.productCode = command.getMainCategoryCode() + command.getSubCategoryCode() + String.format("%05d", sequence);
        this.productName = command.getName();
        this.productEngName = command.getEngName();
        this.mainCategoryCode = command.getMainCategoryCode();
        this.subCategoryCode = command.getSubCategoryCode();
        this.cost = command.getPrice();
        this.isSale = false;
        this.displayStartDate = command.getDisplayStartDate();
        this.displayEndDate = command.getDisplayEndDate();
        this.createProductFeatures(command.getFeatureTypes());
        this.createProductImages(command.getThumbnailImageName(), command.getDetailImageName());
        this.createProductOptions(command.getOptionName(), command.getDefaultOptionName());
        this.createProductPoints(command.getPoints());
    }

    private void createProductImages(String thumbnailImageName, List<String> detailImageName) {
        if(StringUtils.isNotBlank(thumbnailImageName))
            this.images.add(new ProductImage(this, ProductImageCode.THUMBNAIL_IMAGE, thumbnailImageName, this.images.size() + 1));
        if(Objects.nonNull(detailImageName) && !detailImageName.isEmpty()) {
            detailImageName.forEach(name ->
                this.images.add(new ProductImage(this, ProductImageCode.FULL_DETAIL_IMAGE, name, this.images.size() + 1)));
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

    private void createProductFeatures(Set<FeatureType> featureTypes) {
        if(Objects.nonNull(featureTypes) && !featureTypes.isEmpty()) {
            featureTypes.forEach(featureType -> {
                ProductFeature productFeature = new ProductFeature(this);
                productFeature.applyFeatureType(featureType);
                this.features.add(productFeature);
            });
        }
    }

    private void modifyProductFeatures(Set<FeatureType> featureTypes) {
        if(Objects.nonNull(featureTypes) && !featureTypes.isEmpty()) {
            Map<FeatureType, ProductFeature> existingFeatures = this.features.stream()
                    .collect(Collectors.toMap(o -> o.getProductFeatureId().getFeatureType(), o -> o));

            featureTypes.forEach(featureType -> {
                // 기존에 있는 태그면 패스, 없는 태그일 경우만 새 데이터 생성
                if(!existingFeatures.containsKey(featureType)) {
                    ProductFeature productFeature = new ProductFeature(this);
                    productFeature.applyFeatureType(featureType);
                    this.features.add(productFeature);
                }
            });
        } else {
            // 전체 삭제
            this.features.clear();
        }
    }

    public void modifyProduct(ModifyProductCommand command) {
        // 상품이 FO 노출중 (상품 판매중) 일 경우 수정할 수 있는 판매여부, 옵션 재고, 태그만 수정
        this.isSale = command.isSale();
        this.modifyProductFeatures(command.getFeatureTypes());
        if(Objects.nonNull(command.getOptions()) && !command.getOptions().isEmpty()) {
            this.modifyProductOptions(command.getOptions());
        }

        if (!command.isShowProduct()) {
            this.productName = command.getName();
            this.productEngName = command.getEngName();
            this.cost = command.getPrice();
            this.discountRate = command.getDiscount();
            this.modifyProductPoints(command.getPoints());
            this.displayStartDate = command.getDisplayStartDate();
            this.displayEndDate = command.getDisplayEndDate();
        }
    }

    public List<String> getImageNameToDelete(List<Integer> sequenceList) {
        return this.images.stream().filter(image -> !sequenceList.contains(image.getImageSequence())).map(ProductImage::getImgPath).toList();
    }

    /**
     * 상품 이미지 수정
     * 1. 수정하려는 이미지 리스트가 없을 경우 리턴
     * 2. 기존 이미지가 없는 경우 이미지 데이터 전부 새로 생성
     * 3. 기존 이미지가 존재할 경우
     *  3-1. 수정하려는 이미지 리스트 중 삭제된 이미지만 기존 이미지 리스트에서 제거한다.
     * @param command
     */
    public Map<String, List<String>> modifyProductImages(ModifyProductImageCommand command) {
        Map<String, List<String>> map = new HashMap<>();

        if(!command.existModifyImage()) {
            List<String> deleteList = this.images.stream().map(ProductImage::getImgPath).toList();
            map.put(ImageStatus.DELETE, deleteList);
            this.images.clear();
            return map;
        }

        String thumbnailImageName = command.getThumbnailImagePath();
        List<String> detailImageNames = command.getDetailImagePath();

        if(ObjectUtils.isEmpty(this.images)) {
            this.createProductImages(thumbnailImageName, detailImageNames);
            map.put(ImageStatus.ADD, command.getAllImagePath().stream().toList());
            return map;
        }

        // 새 이미지 정보를 담는 리스트 분리
        this.images.forEach(image -> {
            String originImageStatus = this.getImageStatusForModify(command, image);
            if(!ObjectUtils.isEmpty(originImageStatus)) {
                List<String> imgList = map.getOrDefault(originImageStatus, new ArrayList<>());
                imgList.add(image.getImgPath());
                map.put(originImageStatus, imgList);
            }
        });

        map.put(ImageStatus.ADD, command.getAddImagePath());
        this.images.clear();
        this.createProductImages(thumbnailImageName, detailImageNames);

        return map;
    }

    private String getImageStatusForModify(ModifyProductImageCommand command, ProductImage productImage) {
        if(command.isContainImageSequence(productImage.getImageSequence())) {
            // 수정이 필요하지 않은 이미지일 경우
            return ImageStatus.STAY;
        }
        if(!command.isContainImagePath(productImage.getImgPath())) {
            // 삭제해야하는 이미지일 경우
            return ImageStatus.DELETE;
        }
        return ImageStatus.ADD;
    }

    private boolean isOptionExist() { return !ObjectUtils.isEmpty(this.options); }

    private boolean isPointExist() { return !ObjectUtils.isEmpty(this.points); }

    private void modifyProductOptions(Set<ModifyProductCommand.ProductOption> newProductOptions) {
        // 기존 옵션이 존재할 경우
        if(this.isOptionExist()) {
            // 기존 옵션 삭제
            this.options.clear();
        }

        // 새 옵션 데이터 매핑
        newProductOptions.forEach(newOption ->
                this.options.add(new ProductOption(this.productCode, newOption.getSequence(),
                        newOption.getName(), newOption.isDefaultOption(), newOption.getStockCnt()))
        );
    }

    private void addPoint(PointType pointType, int savePoint) {
        this.points.add(new ProductPoint(this.productCode, pointType, savePoint));
    }

    private void modifyProductPoints(Set<ModifyProductCommand.ProductPoint> newPoints) {
        if(this.isPointExist()) {
            this.points.clear();
        }

        for(ModifyProductCommand.ProductPoint point : newPoints) {
            this.addPoint(point.getProductType(), point.getSavePoint());
        }
    }

    private void createProductPoints(Set<CreateProductCommand.ProductPoint> newPoints) {
        for(CreateProductCommand.ProductPoint point : newPoints) {
            this.addPoint(point.getProductType(), point.getSavePoint());
        }
    }
}
