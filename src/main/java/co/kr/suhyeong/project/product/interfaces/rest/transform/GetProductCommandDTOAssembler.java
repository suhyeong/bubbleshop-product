package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.constant.FeatureType;
import co.kr.suhyeong.project.product.domain.model.view.ProductImageView;
import co.kr.suhyeong.project.product.domain.model.view.ProductOptionView;
import co.kr.suhyeong.project.product.domain.model.view.ProductView;
import co.kr.suhyeong.project.product.interfaces.rest.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {FeatureType.class})
public abstract class GetProductCommandDTOAssembler {
    @Named("GetProductListRspDto.GetProductRspDto")
    @Mapping(target = "imageList", source = "imageList", qualifiedByName = "GetProductRspDto.List<GetProductImageDetailRspDto>")
    @Mapping(target = "features", source = "featureTypes", qualifiedByName = "GetProductRspDto.List<GetProductFeatureRspDto>")
    @Mapping(target = "options", source = "options", qualifiedByName = "GetProductRspDto.List<GetProductOptionRspDto>")
    public abstract GetProductRspDto toPrdRspDto(ProductView view);

    @Named("GetProductRspDto.List<GetProductImageDetailRspDto>")
    @Mappings({
            @Mapping(target = "divCode", source = "imageDivCode"),
            @Mapping(target = "path", source = "imagePath"),
            @Mapping(target = "fullUrl", source = "imageFullPath")
    })
    public abstract GetProductImageDetailRspDto toPrdImgRspDto(ProductImageView productImageView);

    @Named("GetProductRspDto.List<GetProductFeatureRspDto>")
    @Mappings({
            @Mapping(target = "code", source = "featureType.code"),
            @Mapping(target = "desc", source = "featureType.desc")
    })
    public abstract GetProductFeatureRspDto toPrdFeatureRspDto(FeatureType featureType);

    @Named("GetProductRspDto.List<GetProductOptionRspDto>")
    @Mappings({
            @Mapping(target = "sequence", source = "optionSequence"),
            @Mapping(target = "name", source = "optionName"),
            @Mapping(target = "stockCnt", source = "stock"),
    })
    public abstract GetProductOptionRspDto toPrdOptionRspDto(ProductOptionView optionView);
}
