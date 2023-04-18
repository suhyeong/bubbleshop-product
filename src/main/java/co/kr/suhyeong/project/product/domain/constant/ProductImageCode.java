package co.kr.suhyeong.project.product.domain.constant;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ProductImageCode {
    THUMBNAIL_IMAGE("T", "썸네일 이미지 정보"),
    FULL_DETAIL_IMAGE("F", "FULL 이미지 정보")

    ;

    private final String code;
    private final String description;

    ProductImageCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final ImmutableMap<String, ProductImageCode> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(ProductImageCode::getCode, Function.identity())));

    public static ProductImageCode find(String code) {
        return codes.getOrDefault(code, null);
    }

//    public static ProductImageCode find(String code) {
//        return null;
//    }
}
