package com.bubbleshop.product.domain.model.aggregate;

import com.bubbleshop.product.domain.command.CreateProductCommand;
import com.bubbleshop.product.domain.command.CreateProductPointCommand;
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
@Builder(toBuilder = true)
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

    @Description("주문 예약 마감일")
    @Column(name = "order_deadline_dt")
    private LocalDateTime orderDeadlineDate;

    @Description("주문수")
    @Column(name = "order_cnt")
    private int orderCount;

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
        this.orderDeadlineDate = command.getOrderDeadlineDate();
        this.createProductFeatures(command.getFeatureTypes());
        this.createProductImages(command.getThumbnailImageName(), command.getDetailImageName());
        this.createProductOptions(command.getOptionName(), command.getDefaultOptionName());
        command.getPoints().forEach(this::createProductPoint);
    }

    private void createProductImages(String thumbnailImageName, List<String> detailImageName) {
        if(StringUtils.isNotBlank(thumbnailImageName))
            this.images.add(new ProductImage(this, ProductImageCode.THUMBNAIL_IMAGE, thumbnailImageName));
        if(Objects.nonNull(detailImageName) && !detailImageName.isEmpty()) {
            detailImageName.forEach(name ->
                this.images.add(new ProductImage(this, ProductImageCode.FULL_DETAIL_IMAGE, name)));
        }
    }

    private void createProductOptions(Set<String> productOptions, String defaultOption) {
        if(Objects.nonNull(productOptions) && !productOptions.isEmpty()) {
            productOptions.forEach(optionName -> {
                boolean isDefaultOption = defaultOption.equals(optionName);
                this.createProductOption(this.options.size() + 1, optionName, isDefaultOption, 0);
            });
        }
    }

    private void createProductOption(int sequence, String optionName, boolean isDefaultOption, int stock) {
        ProductOption productOption = new ProductOption(this);
        productOption.setProductOption(sequence, optionName, isDefaultOption, stock);
        this.options.add(productOption);
    }

    private void createProductFeatures(Set<FeatureType> featureTypes) {
        if(Objects.nonNull(featureTypes) && !featureTypes.isEmpty()) {
            featureTypes.forEach(this::createProductFeature);
        }
    }

    private void createProductFeature(FeatureType featureType) {
        ProductFeature productFeature = new ProductFeature(this);
        productFeature.applyFeatureType(featureType);
        this.features.add(productFeature);
    }

    private void createProductPoint(CreateProductPointCommand newPoint) {
        ProductPoint point = new ProductPoint(this);
        point.setProductPoint(newPoint.getProductType(), newPoint.getSavePoint());
        this.points.add(point);
    }

    private void modifyProductFeatures(Set<FeatureType> featureTypes) {
        if(Objects.nonNull(featureTypes) && !featureTypes.isEmpty()) {
            Map<FeatureType, ProductFeature> existingFeatures = this.features.stream()
                    .collect(Collectors.toMap(o -> o.getProductFeatureId().getFeatureType(), o -> o));

            featureTypes.forEach(featureType -> {
                // 기존에 있는 태그면 패스, 없는 태그일 경우만 새 데이터 생성
                if(!existingFeatures.containsKey(featureType)) {
                    this.createProductFeature(featureType);
                }
            });

            // 없어진 태그일 경우 삭제
            Set<FeatureType> deleteSet = new HashSet<>(existingFeatures.keySet());
            deleteSet.removeAll(featureTypes);
            this.features.removeIf(feature -> deleteSet.contains(feature.getProductFeatureId().getFeatureType()));
        } else {
            // 전체 삭제
            this.features.clear();
        }
    }

    public void modifyProduct(ModifyProductCommand command) {
        // 상품이 FO 노출중 (상품 판매중) 일 경우 수정할 수 있는 판매여부, 옵션 재고, 태그만 수정
        this.isSale = command.isSale();
        this.modifyProductFeatures(command.getFeatureTypes());
        this.modifyProductOptions(command.getOptions());

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

        // 기존 이미지 아이디 Set
        Set<Long> originIdSet = this.images.stream().map(ProductImage::getId).collect(Collectors.toSet());

        // 요청에 포함된 이미지 아이디 (기존 이미지 아이디) Set
        Set<Long> requestSet = command.getImages().stream()
                .map(ModifyProductImageCommand.ProductImage::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 삭제할 이미지 찾기 (기존 O 요청 X)
        Set<Long> deleteSet = new HashSet<>(originIdSet);
        deleteSet.removeAll(requestSet);

        // 삭제할 이미지 제거
        this.images.removeIf(image -> {
            if(deleteSet.contains(image.getId())) {
                List<String> deleteItems = map.getOrDefault(ImageStatus.DELETE, new ArrayList<>());
                deleteItems.add(image.getImgPath());
                map.put(ImageStatus.DELETE, deleteItems);
                return true;
            }

            return false;
        });

        // 추가할 이미지 생성 (기존 X 요청 O)
        command.getImages().forEach(item -> {
            if(item.isNewImage()) {
                this.images.add(new ProductImage(this, item.getImageDivCode(), item.getPath()));
                List<String> addItems = map.getOrDefault(ImageStatus.ADD, new ArrayList<>());
                addItems.add(item.getPath());
                map.put(ImageStatus.ADD, addItems);
            }
        });

        return map;
    }

    private void modifyProductOptions(Set<ModifyProductCommand.ProductOption> newProductOptions) {
        if(Objects.nonNull(newProductOptions) && !newProductOptions.isEmpty()) {
            Map<Integer, ProductOption> existingOptions = this.options.stream()
                    .collect(Collectors.toMap(o -> o.getProductOptionId().getProductOptionSeq(), o -> o));

            for(ModifyProductCommand.ProductOption option : newProductOptions) {
                // 이미 존재하는 옵션일 경우 PK 제외 나머지 값만 업데이트
                if (existingOptions.containsKey(option.getSequence())) {
                    ProductOption originOption = existingOptions.get(option.getSequence());
                    originOption.setProductOption(option.getName(), option.isDefaultOption(), option.getStockCnt());
                }
                // 새로운 옵션일 경우 데이터 추가
                else {
                    this.createProductOption(option.getSequence(), option.getName(), option.isDefaultOption(), option.getStockCnt());
                }
            }
        }
    }

    private void modifyProductPoints(Set<CreateProductPointCommand> newPoints) {
        Map<PointType, ProductPoint> existingPoints = this.points.stream()
                .collect(Collectors.toMap(o -> o.getProductPointId().getPointType(), o -> o));

        for(CreateProductPointCommand requestPoint : newPoints) {
            // 이미 있는 포인트 유형일 경우 포인트 값만 수정하도록 처리
            if (existingPoints.containsKey(requestPoint.getProductType())) {
                ProductPoint point = existingPoints.get(requestPoint.getProductType());
                point.setSavePoints(requestPoint.getSavePoint());
            }
            // 기존에 없는 포인트 유형일 경우 새로 추가
            else {
                this.createProductPoint(requestPoint);
            }
        }
    }
}
