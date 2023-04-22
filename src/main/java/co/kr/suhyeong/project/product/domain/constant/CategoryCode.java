package co.kr.suhyeong.project.product.domain.constant;

import lombok.Getter;

public enum CategoryCode {


    ;

    @Getter
    private final String prefixCode;
    private final String mainCode;
    private final String subCode;
    private final String description;

    CategoryCode(String prefixCode, String mainCode, String subCode, String description) {
        this.prefixCode = prefixCode;
        this.mainCode = mainCode;
        this.subCode = subCode;
        this.description = description;
    }
}
