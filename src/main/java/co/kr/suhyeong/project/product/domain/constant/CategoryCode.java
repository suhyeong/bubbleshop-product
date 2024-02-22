package co.kr.suhyeong.project.product.domain.constant;

import co.kr.suhyeong.project.constants.ResponseCode;
import co.kr.suhyeong.project.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum CategoryCode {
    CATEGORY_01("01", "01", "01", "카테고리 01"),
    CATEGORY_02("02", "02", "02", "카테고리 02"),

    ;

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

    private static final ImmutableMap<String, CategoryCode> mainCodes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(CategoryCode::getMainCode, Function.identity())));

    private static final ImmutableMap<String, CategoryCode> subCodes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(CategoryCode::getSubCode, Function.identity())));

    // TODO Main Category Code, Sub Category Code 분리
    public static CategoryCode find(String value) {
        if(!mainCodes.containsKey(value) || !subCodes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_OPTION_TYPE);

        return mainCodes.get(value);
    }
}
