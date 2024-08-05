package co.kr.suhyeong.project.product.domain.model.valueobject;

import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Stock {
    @Description("상품 재고")
    @Column(name = "product_stock_cnt")
    private int count;

    public void applyStockCount(int count) {
        this.count = count;
    }
}
