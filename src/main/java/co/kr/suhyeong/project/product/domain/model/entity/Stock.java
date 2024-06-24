package co.kr.suhyeong.project.product.domain.model.entity;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_stock")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Stock extends TimeEntity {
    @Id
    @Description("상품 코드")
    @Column(name = "product_code")
    private String productCode;

    @Description("상품 재고")
    @Column(name = "product_cnt")
    private int count;

    /**
     * 재고 Constructor
     * 기본 생성시 재고는 0 으로 세팅한다.
     * @param product 상품 Entity
     */
    public Stock(Product product) {
        this.productCode = product.getProductCode();
        this.count = 0;
    }
}
