package co.kr.suhyeong.project.product.domain.model.entity;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.converter.ProductImageCodeConverter;
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
    
    @Description("이미지 구분 코드")
    @Column(name = "img_div_code")
    @Convert(converter = ProductImageCodeConverter.class)
    private ProductImageCode divCode;

    @Description("이미지 경로")
    @Column(name = "img_path")
    private String imgPath;

    public ProductImage(Product product, CreateProductCommand command) {
        this.productImageId = new ProductImageId(product.getProductCode(), 1);
        this.divCode = ProductImageCode.THUMBNAIL_IMAGE;
        this.imgPath = command.getThumbnailImagePath();
    }

    public void modifyProductThumbnailImagePath(String changePath) {
        this.imgPath = changePath;
    }
}
