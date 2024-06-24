package co.kr.suhyeong.project.product.domain.model.entity;

import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_img_mng")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ProductImage extends TimeEntity {
    @EmbeddedId
    private ProductImageId productImageId;

    @Description("이미지 경로")
    @Column(name = "img_path")
    private String imgPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    @ToString.Exclude
    private Product product;

    public ProductImage(Product product, ProductImageCode imageCode, String imagePath) {
        this.productImageId = new ProductImageId(product.getProductCode(), imageCode);
        this.imgPath = imagePath;
    }

    public String getImageDivCode() {
        return this.productImageId.getDivCode().getCode();
    }

    public void modifyProductThumbnailImagePath(String changePath) {
        this.imgPath = changePath;
    }

    @JsonIgnore
    public boolean isThumbnailImage() {
        return this.productImageId.isThumbnailImage();
    }

    @JsonIgnore
    public boolean isDetailImage() {
        return this.productImageId.isDetailImage();
    }
}
