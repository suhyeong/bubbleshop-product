package co.kr.suhyeong.project.product.domain.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class ModifyProductCommand extends CreateProductCommand {
    private String productCode;
    private int discount;
    private boolean isSale;
}
