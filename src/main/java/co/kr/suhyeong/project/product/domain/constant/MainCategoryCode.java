package co.kr.suhyeong.project.product.domain.constant;

import co.kr.suhyeong.project.constants.ResponseCode;
import co.kr.suhyeong.project.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum MainCategoryCode {
    MAIN_CATEGORY_01("01", "메인 카테고리 01"),
    MAIN_CATEGORY_02("02", "메인 카테고리 02"),

    ;

    private final String code;
    private final String description;

    MainCategoryCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final ImmutableMap<String, MainCategoryCode> mainCodes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(MainCategoryCode::getCode, Function.identity())));

    public static MainCategoryCode find(String value) {
        if(!mainCodes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_CATEGORY_TYPE);

        return mainCodes.get(value);
    }
}
