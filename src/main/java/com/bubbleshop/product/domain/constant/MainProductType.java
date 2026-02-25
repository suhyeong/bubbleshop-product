package com.bubbleshop.product.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum MainProductType {
    NEW("N", "신상순"),
    RESERVE_CLOSE("C", "예약 마감순"),
    STOCK_UP("S", "재입고순"),
    POPULARITY("P", "인기순"),
    ;

    private final String code;
    private final String desc;

    private static final ImmutableMap<String, MainProductType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(MainProductType::getCode, Function.identity())));

    MainProductType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MainProductType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_OPTION_TYPE);

        return codes.get(value);
    }

}
