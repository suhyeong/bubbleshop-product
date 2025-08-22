package com.bubbleshop.product.interfaces.rest.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModifyProductPointReqDto {
    private String pointTypeCode;
    private int savePoint;
}
