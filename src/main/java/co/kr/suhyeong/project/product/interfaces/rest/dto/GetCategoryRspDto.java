package co.kr.suhyeong.project.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCategoryRspDto {
    private String categoryCode;
    private String categoryType;
    private String categoryName;
    private String categoryEngName;
    private Boolean isShow;
}
