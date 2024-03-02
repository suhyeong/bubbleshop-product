package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.product.domain.constant.CategoryCode;
import co.kr.suhyeong.project.product.domain.constant.OptionType;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CreateProductCommand {
    private CategoryCode categoryCode;
    private String name;
    private int price;
    private Set<OptionType> options;
    private String thumbnailImagePath;
    private String detailImagePath;
    private boolean isSale;

    public boolean isThumbnailImageExist() {
        return !this.thumbnailImagePath.isBlank();
    }

    public boolean isDetailImageExist() {
        return !this.detailImagePath.isBlank();
    }
}
