package co.kr.suhyeong.project.product.domain.model.view;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryListView {
    private long count;
    private List<CategoryView> categoryList;

    public CategoryListView(List<CategoryView> list) {
        this.count = list.size();
        this.categoryList = list;
    }

    public CategoryListView(long count, List<CategoryView> list) {
        this.count = count;
        this.categoryList = list;
    }
}
