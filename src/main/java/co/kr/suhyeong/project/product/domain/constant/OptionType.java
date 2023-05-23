package co.kr.suhyeong.project.product.domain.constant;

import co.kr.suhyeong.project.exception.InvalidTypeException;
import co.kr.suhyeong.project.constants.ResponseCode;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OptionType {


    ;

    @Getter
    private final String typeCode;

    private static final ImmutableMap<String, OptionType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(OptionType::getTypeCode, Function.identity())));

    OptionType(String typeCode) {
        this.typeCode = typeCode;
    }

    public static OptionType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_OPTION_TYPE);

        return codes.get(value);
    }
}
