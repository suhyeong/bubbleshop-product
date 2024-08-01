package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.product.domain.constant.FeatureType;
import co.kr.suhyeong.project.product.domain.model.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyProductCommand {
    private String productCode;

    private String name;
    private String engName;
    private int price;
    private Set<FeatureType> featureTypes;
    private int discount;
    private boolean isSale;
    private Set<ProductOption> options;

    @Builder
    @Getter
    public static class ProductOption {
        private int sequence;
        private String name;
        private int stockCnt;
        private boolean isDefaultOption;
    }
}
