package co.kr.suhyeong.project.product.domain.command;

import co.kr.suhyeong.project.constants.CategoryCode;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateProductCommand {
    private CategoryCode categoryCode;
    private String name;
    private int price;
    private List<String> options;
}
