package co.kr.suhyeong.project.product.domain.model.aggregate;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;
import co.kr.suhyeong.project.product.domain.model.converter.MainCategoryCodeConverter;
import co.kr.suhyeong.project.product.domain.model.converter.SubCategoryCodeConverter;
import co.kr.suhyeong.project.product.domain.model.converter.YOrNToBooleanConverter;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.model.entity.Stock;
import co.kr.suhyeong.project.product.domain.model.entity.TimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.jfr.Description;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Description("상품 메인 카테고리 코드")
    @Column(name = "main_cate_code")
    @Convert(converter = MainCategoryCodeConverter.class)
    private MainCategoryCode mainCategoryCode;

    @Description("상품 서브 카테고리 코드")
    @Column(name = "sub_cate_code")
    @Convert(converter = SubCategoryCodeConverter.class)
    private SubCategoryCode subCategoryCode;

    @Description("상품 원가")
    @Column(name = "cost")
    private int cost;

    @Description("할인율")
    @Column(name = "disc_rate")
    private Double discount_rate;

    @Description("판매 여부")
    @Convert(converter = YOrNToBooleanConverter.class)
    @Column(name = "sale_yn")
    private boolean isSale;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"product"})
    private List<ProductImage> images = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    @JsonIgnoreProperties({"product"})
    private Stock stock;

    public Product(CreateProductCommand command, int sequence) {
        this.productCode = command.getMainCategoryCode().getCode() + command.getSubCategoryCode().getCode() + String.format("%05d", sequence);
        this.productName = command.getName();
        this.mainCategoryCode = command.getMainCategoryCode();
        this.subCategoryCode =  command.getSubCategoryCode();
        this.cost = command.getPrice();
        this.discount_rate = 0.0;
        this.isSale = false;
        this.createProductImages(command);
        this.createProductStock();
    }

    private void createProductImages(CreateProductCommand command) {
        if(command.isThumbnailImageExist())
            this.images.add(new ProductImage(this, ProductImageCode.THUMBNAIL_IMAGE, command.getThumbnailImagePath()));
        if(command.isDetailImageExist())
            this.images.add(new ProductImage(this, ProductImageCode.FULL_DETAIL_IMAGE, command.getDetailImagePath()));
    }

    private void createProductStock() {
        this.stock = new Stock(this);
    }

    public int getProductStock() {
        return this.stock.getCount();
    }

    public void modifyProduct(ModifyProductCommand command) {
        this.productName = command.getName();
        this.mainCategoryCode = command.getMainCategoryCode();
        this.subCategoryCode = command.getSubCategoryCode();
        this.cost = command.getPrice();
        this.discount_rate = command.getDiscount();
        this.isSale = command.isSale();
        this.modifyProductImage(command);
    }

    private void modifyProductImage(ModifyProductCommand command) {
        if(Objects.nonNull(this.images) && !this.images.isEmpty()) {
            this.images.forEach(item -> item.modifyImagePath(command));
        }
    }
}
