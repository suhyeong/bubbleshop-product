package com.bubbleshop.product.domain.command;

import com.bubbleshop.product.domain.constant.FeatureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
@SuperBuilder
public class CreateProductCommand {
    private String mainCategoryCode;
    private String subCategoryCode;
    private String name;
    private String engName;
    private int price;
    private Set<FeatureType> featureTypes;
    private Set<String> optionName;
    private String defaultOptionName;
    private String thumbnailImageName;
    private List<String> detailImageName;

    public boolean isThumbnailImageExist() {
        return StringUtils.isNotBlank(this.thumbnailImageName);
    }

    public boolean isDetailImageExist() {
        return Objects.nonNull(detailImageName) && !this.detailImageName.isEmpty();
    }
}
