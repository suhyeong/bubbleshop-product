package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetCategoriesCommand {
    private boolean isNeedToPaging;
    private Pageable paging;
    private String categoryCode;
    private CategoryType categoryType;

    public boolean isCategoryCodeExist() {
        return StringUtils.isNotBlank(categoryCode);
    }
}
