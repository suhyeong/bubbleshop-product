package co.kr.suhyeong.project.product.domain.model.entity;

import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.converter.YOrNToBooleanConverter;
import co.kr.suhyeong.project.product.domain.model.valueobject.Stock;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_option_master")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOption extends TimeEntity {
    @EmbeddedId
    private ProductOptionId productOptionId;

    @Description("옵션명")
    @Column(name = "product_opt_name")
    private String optionName;

    @Description("디폴트 옵션 여부")
    @Column(name = "product_opt_default_yn")
    @Convert(converter = YOrNToBooleanConverter.class)
    private boolean isDefaultOption;

    @Embedded
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", insertable = false, updatable = false)
    @ToString.Exclude
    private Product product;

    public ProductOption(String productCode, int sequence,
                         String optionName, boolean isDefaultOption) {
        this.productOptionId = new ProductOptionId(productCode, sequence);
        this.optionName = optionName;
        this.isDefaultOption = isDefaultOption;
        this.stock = new Stock();
    }

    public ProductOption(String productCode, int sequence,
                         String optionName, boolean isDefaultOption, int stock) {
        this(productCode, sequence, optionName, isDefaultOption);
        this.stock.applyStockCount(stock);
    }

    public int getOptionSequence() {
        return this.productOptionId.getProductOptionSeq();
    }
}
