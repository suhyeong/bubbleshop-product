package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;
import co.kr.suhyeong.project.product.domain.constant.OptionType;
import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CreateProductCommand {
    private MainCategoryCode mainCategoryCode;
    private SubCategoryCode subCategoryCode;
    private String name;
    private int price;
    private Set<OptionType> options;
    private String thumbnailImagePath;
    private String detailImagePath;

    public boolean isThumbnailImageExist() {
        return !this.thumbnailImagePath.isBlank();
    }

    public boolean isDetailImageExist() {
        return !this.detailImagePath.isBlank();
    }
}
