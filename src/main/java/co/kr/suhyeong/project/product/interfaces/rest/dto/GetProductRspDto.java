package co.kr.suhyeong.project.product.interfaces.rest.dto;

import co.kr.suhyeong.project.product.domain.model.view.ProductImageView;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProductRspDto {
    private String productCode;
    private String productName;
    private String createdAt;

    private String mainCategoryCode;
    private String mainCategoryName;
    private String subCategoryCode;
    private String subCategoryName;

    private int price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductImageView> imageList;
}
