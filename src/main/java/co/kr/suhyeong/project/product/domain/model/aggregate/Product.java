package co.kr.suhyeong.project.product.domain.model.aggregate;

import co.kr.suhyeong.project.product.domain.model.converter.YOrNToBooleanConverter;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@Table(name = ) // TODO 테이블 이름 작성
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AbstractAggregateRoot<Product> implements Serializable {

    @Id
    @Description("상품 코드")
    private String productCode;

    @Description("상품 이름")
    private String productName;

    @Description("상품 메인 카테고리 코드")
    private String mainCategoryCode;

    @Description("상품 서브 카테고리 코드")
    private String subCategoryCode;

    @Description("상품 원가")
    private int cost;

    @Description("할인율")
    private Double discount_rate;

    @Description("판매 여부")
    @Convert(converter = YOrNToBooleanConverter.class)
    private boolean saleYn;

    // TODO 상품 Aggregate 와 상품 이미지 Entity 를 1:N 관계로 매핑할 것
    @Description("상품 이미지 리스트")
    @OneToMany
    @MapsId("productCode")
    private List<ProductImage> productImageList;
}
