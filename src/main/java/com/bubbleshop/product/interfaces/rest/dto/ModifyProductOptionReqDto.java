package com.bubbleshop.product.interfaces.rest.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModifyProductOptionReqDto {
    private int sequence;
    private String name;
    private int stockCnt;
    private Boolean isDefaultOption;
}
