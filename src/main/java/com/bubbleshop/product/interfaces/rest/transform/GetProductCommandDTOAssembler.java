package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.model.view.*;
import com.bubbleshop.product.interfaces.rest.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {FeatureType.class})
public abstract class GetProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "imageList", source = "imageList", qualifiedByName = "GetProductRspDto.List<GetProductImageDetailRspDto>"),
            @Mapping(target = "features", source = "featureTypes", qualifiedByName = "GetProductRspDto.List<GetProductFeatureRspDto>"),
            @Mapping(target = "options", source = "options", qualifiedByName = "GetProductRspDto.List<GetProductOptionRspDto>"),
            @Mapping(target = "points", source = "points", qualifiedByName = "GetProductRspDto.List<GetProductPointRspDto>")
    })
    public abstract GetProductRspDto toPrdRspDto(ProductView view);

    @Named("GetProductRspDto.List<GetProductImageDetailRspDto>")
    @Mappings({
            @Mapping(target = "sequence", source = "imageSequence"),
            @Mapping(target = "divCode", source = "imageDivCode"),
            @Mapping(target = "path", source = "imagePath"),
            @Mapping(target = "fullUrl", source = "imageFullPath")
    })
    public abstract GetProductImageDetailRspDto toPrdImgRspDto(ProductImageView productImageView);

    @Named("GetProductRspDto.List<GetProductFeatureRspDto>")
    @Mappings({
            @Mapping(target = "code", source = "featureView.featureType.code"),
            @Mapping(target = "desc", source = "featureView.featureType.desc")
    })
    public abstract GetProductFeatureRspDto toPrdFeatureRspDto(ProductFeatureView featureView);

    @Named("GetProductRspDto.List<GetProductOptionRspDto>")
    @Mappings({
            @Mapping(target = "sequence", source = "optionSequence"),
            @Mapping(target = "name", source = "optionName"),
            @Mapping(target = "stockCnt", source = "stock"),
    })
    public abstract GetProductOptionRspDto toPrdOptionRspDto(ProductOptionView optionView);

    @Named("GetProductRspDto.List<GetProductPointRspDto>")
    @Mappings({
            @Mapping(target = "pointTypeCode", source = "pointView.pointType.code"),
            @Mapping(target = "savePoint", source = "savePoint")
    })
    public abstract GetProductPointRspDto toPrdPointRspDto(ProductPointView pointView);
}
