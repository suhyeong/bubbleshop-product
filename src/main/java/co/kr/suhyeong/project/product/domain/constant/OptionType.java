package co.kr.suhyeong.project.product.domain.constant;

import co.kr.suhyeong.project.exception.InvalidTypeException;
import co.kr.suhyeong.project.constants.ResponseCode;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OptionType {
    OPTION_01("01", "옵션 01"),
    OPTION_02("02", "옵션 02")
    ;

    @Getter
    private final String code;
    private final String desc;

    private static final ImmutableMap<String, OptionType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(OptionType::getCode, Function.identity())));

    OptionType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OptionType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_OPTION_TYPE);

        return codes.get(value);
    }
}
