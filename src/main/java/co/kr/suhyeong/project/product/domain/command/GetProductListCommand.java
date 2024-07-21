package co.kr.suhyeong.project.product.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetProductListCommand {
    private Pageable pageable;

    private String productCode;
    private String productName;
    private boolean isNameContains;

    private String mainCategoryCode;
    private String subCategoryCode;
}
