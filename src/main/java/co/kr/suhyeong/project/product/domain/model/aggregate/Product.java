package co.kr.suhyeong.project.product.domain.model.aggregate;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyProductImageCommand;
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
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static co.kr.suhyeong.project.constants.StaticValues.ImageStatus;

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

    @OneToMany(mappedBy = "product", targetEntity = ProductImage.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductImage> images = new ArrayList<>();

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
        this.createProductImages(command.getThumbnailImageName(), command.getDetailImageName());
        this.createProductOptions(command.getOptionName(), command.getDefaultOptionName());
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

    public void modifyProduct(ModifyProductCommand command) {
        this.productName = command.getName();
        this.productEngName = command.getEngName();
        this.cost = command.getPrice();
        this.discount_rate = command.getDiscount();
        this.isSale = command.isSale();
        this.featureTypes = command.getFeatureTypes();
        this.modifyProductOptions(command.getOptions());
    }

    public List<String> getImageNameToDelete(List<Integer> sequenceList) {
        return this.images.stream().filter(image -> !sequenceList.contains(image.getImageSequence())).map(ProductImage::getImgPath).collect(Collectors.toList());
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
            List<String> deleteList = this.images.stream().map(ProductImage::getImgPath).collect(Collectors.toList());
            map.put(ImageStatus.DELETE, deleteList);
            this.images.clear();
            return map;
        }

        String thumbnailImageName = command.getThumbnailImagePath();
        List<String> detailImageNames = command.getDetailImagePath();

        if(Objects.isNull(this.images) || this.images.isEmpty()) {
            this.createProductImages(thumbnailImageName, detailImageNames);
            map.put(ImageStatus.ADD, command.getAllImagePath());
            return map;
        }

        // 새 이미지 정보를 담는 리스트 분리
        this.images.forEach(image -> {
            String originImageStatus = this.getImageStatusForModify(command, image);
            if(originImageStatus != null) {
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
        if(command.getNotModifyImageSequences().contains(productImage.getImageSequence())) {
            // 수정이 필요하지 않은 이미지일 경우
            return ImageStatus.STAY;
        }
        if(!command.getAllImagePath().contains(productImage.getImgPath())) {
            // 삭제해야하는 이미지일 경우
            return ImageStatus.DELETE;
        }
        return null;
    }

    private boolean isOptionExist() {
        return Objects.nonNull(this.options) && !this.options.isEmpty();
    }

    private void modifyProductOptions(Set<ModifyProductCommand.ProductOption> newProductOptions) {
        // 기존 옵션이 존재할 경우
        if(this.isOptionExist()) {
            // 기존 옵션 삭제
            this.options.clear();

            // 새 옵션 데이터 매핑
            newProductOptions.forEach(newOption -> {
                this.options.add(new ProductOption(this.productCode, newOption.getSequence(),
                        newOption.getName(), newOption.isDefaultOption(), newOption.getStockCnt()));
            });
        }
    }
}
