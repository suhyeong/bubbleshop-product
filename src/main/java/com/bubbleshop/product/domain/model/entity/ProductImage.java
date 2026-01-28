package com.bubbleshop.product.domain.model.entity;

import com.bubbleshop.product.domain.constant.ProductImageCode;
import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.converter.ProductImageCodeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_image_master")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class ProductImage extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 6780321522174286346L;

    @Id
    @Description("상품 이미지 아이디")
    @Column(name = "product_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Description("이미지 구분 코드")
    @Column(name = "img_div_code")
    @Convert(converter = ProductImageCodeConverter.class)
    private ProductImageCode divCode;

    @Description("이미지 경로")
    @Column(name = "img_path")
    private String imgPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    @ToString.Exclude
    private Product product;

    public ProductImage(Product product, ProductImageCode imageCode, String imagePath) {
        this.product = product;
        this.divCode = imageCode;
        this.imgPath = imagePath;
    }

    @JsonIgnore
    public boolean isThumbnailImage() {
        return this.divCode.equals(ProductImageCode.THUMBNAIL_IMAGE);
    }

    @JsonIgnore
    public boolean isDetailImage() {
        return this.divCode.equals(ProductImageCode.FULL_DETAIL_IMAGE);
    }

    public String getImageDivCode() {
        return this.divCode.getCode();
    }
}
