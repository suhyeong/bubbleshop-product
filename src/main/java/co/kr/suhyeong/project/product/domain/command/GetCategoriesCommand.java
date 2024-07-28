package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetCategoriesCommand {
    private boolean isNeedToPaging;
    private Pageable paging;
    private String categoryCode;
    private CategoryType categoryType;
    private String categoryName;
    private boolean isCategoryNameContains;

    public boolean isCategoryCodeExist() {
        return StringUtils.isNotBlank(categoryCode);
    }

    public boolean isCategoryTypeExist() {
        return Objects.nonNull(categoryType);
    }
}
