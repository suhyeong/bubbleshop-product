package co.kr.suhyeong.project.product.domain.model.aggregate;

import co.kr.suhyeong.project.product.domain.model.converter.YOrNToBooleanConverter;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product_master")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractAggregateRoot<Product> implements Serializable {

    @Id
    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("상품 이름")
    @Column(name = "product_nm")
    private String productName;

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
    private Double discount_rate;

    @Description("판매 여부")
    @Convert(converter = YOrNToBooleanConverter.class)
    @Column(name = "sale_yn")
    private boolean saleYn;

    @Description("생성 일시")
    @Column(name = "crt_dt")
    private LocalDateTime createdDate;

    @Description("수정 일시")
    @Column(name = "chn_dt")
    private LocalDateTime modifiedDate;

    // TODO 상품 Aggregate 와 상품 이미지 Entity 를 1:N 관계로 매핑할 것
//    @Description("상품 이미지 리스트")
//    @OneToMany(mappedBy = "productImageId.productCode")
//    private List<ProductImage> productImageList;
}
