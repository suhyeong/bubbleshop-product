package com.bubbleshop.product.domain.command;

import com.bubbleshop.product.domain.constant.CategoryType;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateCategoryCommand {
    private String categoryCode;
    private String categoryName;
    private String categoryEngName;
    private CategoryType categoryType;
    private boolean isShow;
}
