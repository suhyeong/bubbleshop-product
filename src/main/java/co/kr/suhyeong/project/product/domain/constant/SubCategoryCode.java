package co.kr.suhyeong.project.product.domain.constant;

import co.kr.suhyeong.project.constants.ResponseCode;
import co.kr.suhyeong.project.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum SubCategoryCode {
    SUB_CATEGORY_01( "01", "서브 카테고리 01"),
    SUB_CATEGORY_02( "02", "서브 카테고리 02"),

    ;

    private final String code;
    private final String description;

    SubCategoryCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final ImmutableMap<String, SubCategoryCode> subCodes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(SubCategoryCode::getCode, Function.identity())));

    public static SubCategoryCode find(String value) {
        if(subCodes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_CATEGORY_TYPE);

        return subCodes.get(value);
    }
}
