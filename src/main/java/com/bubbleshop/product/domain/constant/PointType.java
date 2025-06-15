package com.bubbleshop.product.domain.constant;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.InvalidTypeException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum PointType {
    REVIEW("R", "일반 리뷰"),
    PHOTO_REVIEW("P", "사진 리뷰"),
    PAYMENT_CONFIRM("C", "구매 확정"),
    ;

    private final String code;
    private final String desc;

    private static final ImmutableMap<String, PointType> codes = ImmutableMap.copyOf(
            Stream.of(values()).collect(Collectors.toMap(PointType::getCode, Function.identity())));

    PointType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PointType find(String value) {
        if(!codes.containsKey(value))
            throw new InvalidTypeException(ResponseCode.INVALID_POINT_TYPE);

        return codes.get(value);
    }
}
