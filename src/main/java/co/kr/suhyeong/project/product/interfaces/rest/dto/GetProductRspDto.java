package co.kr.suhyeong.project.product.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetProductRspDto {
    private String productCode;
    private String productName;
    private String productEngName;
    private String createdAt;

    private String mainCategoryCode;
    private String mainCategoryName;
    private String subCategoryCode;
    private String subCategoryName;

    private int price;
    private int discountRate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GetProductImageDetailRspDto> imageList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GetProductFeatureRspDto> features;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GetProductOptionRspDto> options;
}
