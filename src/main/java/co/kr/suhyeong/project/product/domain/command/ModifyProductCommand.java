package co.kr.suhyeong.project.product.domain.command;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ModifyProductCommand extends CreateProductCommand {
    private String productCode;
    private Double discount;
    private boolean isSale;
}
