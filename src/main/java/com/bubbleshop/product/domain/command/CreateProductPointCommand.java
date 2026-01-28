package com.bubbleshop.product.domain.command;

import com.bubbleshop.product.domain.constant.PointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductPointCommand {
    private PointType productType;
    private int savePoint;
}
