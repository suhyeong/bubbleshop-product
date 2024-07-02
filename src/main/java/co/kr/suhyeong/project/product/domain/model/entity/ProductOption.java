package co.kr.suhyeong.project.product.domain.model.entity;

import jdk.jfr.Description;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product_option_master")
@Getter
@ToString
public class ProductOption {
    @EmbeddedId
    private ProductOptionId productOptionId;

    @Description("옵션 설명")
    @Column(name = "product_opt_desc")
    private String optionDescription;

    
}
