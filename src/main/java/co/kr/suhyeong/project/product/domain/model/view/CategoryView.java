package co.kr.suhyeong.project.product.domain.model.view;

import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import co.kr.suhyeong.project.product.domain.model.aggregate.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryView {
    private String categoryCode;
    private CategoryType categoryType;
    private String categoryName;
    private String categoryEngName;
    private Boolean isShow;

    public CategoryView(Category category) {
        this.categoryCode = category.getCode();
        this.categoryType = category.getCategoryType();
        this.categoryName = category.getName();
        this.categoryEngName = category.getEngName();
        this.isShow = category.isShow();
    }
}
