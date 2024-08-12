package com.bubbleshop.product.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum FeatureType {
    RANDOM("R", "랜덤 상품"),
    FIX("F", "고정 상품"),
    NEW("N", "신상품"),
    STOCK_UP("S", "재입고"),
    ;

    private final String code;
    private final String desc;

    private static final ImmutableMap<String, FeatureType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(FeatureType::getCode, Function.identity())));

    FeatureType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FeatureType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_OPTION_TYPE);

        return codes.get(value);
    }
}
