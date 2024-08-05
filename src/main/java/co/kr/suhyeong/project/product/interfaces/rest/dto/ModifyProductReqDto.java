package co.kr.suhyeong.project.product.interfaces.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyProductReqDto {
    private List<String> features;
    private String name;
    private String engName;
    private int price;
    private int discount;
    private boolean isSale;
    private List<ModifyProductOptionReqDto> options;
}
