package co.kr.suhyeong.project.product.domain.model.aggregate;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.model.converter.YOrNToBooleanConverter;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_master")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime createdDate;

    @Description("수정 일시")
    @Column(name = "chn_dt")
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Product(CreateProductCommand command) {
        this.productCode = "01"; // TODO 메인 카테고리, 서브 카테고리, 현재 일자 기준으로 규칙 만들기
        this.productName = command.getName();
        this.mainCategoryCode = command.getCategoryCode().getMainCode();
        this.subCategoryCode =  command.getCategoryCode().getSubCode();
        this.cost = command.getPrice();
        this.discount_rate = 0.0;
        this.saleYn = command.isSale();
    }
}
