package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;
import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@NoArgsConstructor
@SuperBuilder
public class CreateProductCommand {
    private MainCategoryCode mainCategoryCode;
    private SubCategoryCode subCategoryCode;
    private String name;
    private int price;
    private Set<String> optionName;
    private String defaultOptionName;
    private String thumbnailImagePath;
    private String detailImagePath;

    public boolean isThumbnailImageExist() {
        return !this.thumbnailImagePath.isBlank();
    }

    public boolean isDetailImageExist() {
        return !this.detailImagePath.isBlank();
    }
}
