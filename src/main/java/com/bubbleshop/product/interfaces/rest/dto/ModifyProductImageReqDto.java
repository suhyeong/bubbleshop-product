package com.bubbleshop.product.interfaces.rest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ModifyProductImageReqDto {
    private List<ImageReqDto> images;

    @Getter
    @Builder
    public static class ImageReqDto {
        private String divCode;
        private Integer sequence;
        private String fileName;
    }
}
