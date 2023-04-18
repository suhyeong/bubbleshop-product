package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class GetProductImageCommand {
    private String productCode;
    private List<ProductImageCode> productImageCodeList;
}
