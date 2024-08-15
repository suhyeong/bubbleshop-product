package com.bubbleshop.product.domain.model.aggregate;

import com.bubbleshop.product.domain.command.ModifyCategoryCommand;
import com.bubbleshop.product.domain.constant.CategoryType;
import com.bubbleshop.product.domain.model.converter.CategoryTypeConverter;
import com.bubbleshop.product.domain.model.converter.YOrNToBooleanConverter;
import com.bubbleshop.product.domain.model.entity.TimeEntity;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category_master")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Category extends TimeEntity implements Serializable {
    @Id
    @Description("카테고리 코드")
    @Column(name = "cate_code")
    private String code;

    @Description("카테고리명")
    @Column(name = "cate_name")
    private String name;

    @Description("카테고리명")
    @Column(name = "cate_eng_name")
    private String engName;

    @Description("카테고리 타입")
    @Column(name = "cate_type")
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType categoryType;

    @Description("노출 여부")
    @Column(name = "show_yn")
    @Convert(converter = YOrNToBooleanConverter.class)
    private boolean isShow;

    public void modifyCategoryInfo(ModifyCategoryCommand command) {
        this.name = command.getCategoryName();
        this.engName = command.getCategoryEngName();
        this.categoryType = command.getCategoryType();
        this.isShow = command.isShow();
    }
}
