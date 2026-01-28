package com.bubbleshop.product.domain.model.entity;

import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.converter.YOrNToBooleanConverter;
import com.bubbleshop.product.domain.model.valueobject.Stock;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "product_option_master")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOption extends TimeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -1849905011411438659L;

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

    @MapsId("productCode")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code")
    @ToString.Exclude
    private Product product;

    public ProductOption(Product product) {
        this.product = product;
        this.productOptionId = new ProductOptionId();
        this.stock = new Stock();
    }

    public void setProductOption(int sequence, String optionName, boolean isDefaultOption, int stock) {
        this.productOptionId.setProductOptionSeq(sequence);
        this.setProductOption(optionName, isDefaultOption, stock);
    }

    public void setProductOption(String optionName, boolean isDefaultOption, int stock) {
        this.optionName = optionName;
        this.isDefaultOption = isDefaultOption;
        this.stock.applyStockCount(stock);
    }
}
