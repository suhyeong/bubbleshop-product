package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.model.view.MainProductView;
import com.bubbleshop.product.domain.model.view.ProductFeatureView;
import com.bubbleshop.product.domain.model.view.ProductImageView;
import com.bubbleshop.product.interfaces.rest.dto.GetMainProductListRspDto;
import com.bubbleshop.product.interfaces.rest.dto.GetMainProductRspDto;
import com.bubbleshop.product.interfaces.rest.dto.GetProductFeatureRspDto;
import com.bubbleshop.product.interfaces.rest.dto.GetProductImageDetailRspDto;
import com.bubbleshop.util.DateTimeUtils;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, imports = { DateTimeUtils.class })
public abstract class GetMainProductListCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "mainType", source = "mainType"),
            @Mapping(target = "list", source = "mainProductViewList", qualifiedByName = "GetMainProductListRspDto.GetMainProductRspDto")
    })
    public abstract GetMainProductListRspDto toRspDto(String mainType, List<MainProductView> mainProductViewList);

    @Named("GetMainProductListRspDto.GetMainProductRspDto")
    @Mappings({
            @Mapping(target = "image", source = "thumbnailImage", qualifiedByName = "GetMainProductRspDto.GetProductImageDetailRspDto"),
            @Mapping(target = "features", source = "featureTypes", qualifiedByName = "GetMainProductRspDto.List<GetProductFeatureRspDto>"),
            @Mapping(target = "orderDeadlineDate", expression = "java( DateTimeUtils.convertDateTimeToString(DateTimeUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, view.getOrderDeadlineDate()) )")
    })
    public abstract GetMainProductRspDto toDetailRspDto(MainProductView view);

    @Named("GetMainProductRspDto.List<GetProductFeatureRspDto>")
    @Mappings({
            @Mapping(target = "code", source = "featureView.featureType.code"),
            @Mapping(target = "desc", source = "featureView.featureType.desc")
    })
    public abstract GetProductFeatureRspDto toFeatureDetailRspDto(ProductFeatureView featureView);

    @Named("GetMainProductRspDto.GetProductImageDetailRspDto")
    @Mappings({
            @Mapping(target = "divCode", source = "imageDivCode"),
            @Mapping(target = "path", source = "imagePath"),
            @Mapping(target = "fullUrl", source = "imageFullPath")
    })
    public abstract GetProductImageDetailRspDto toImageDetailRspDto(ProductImageView productImageView);
}
