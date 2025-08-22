package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.ModifyProductCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.constant.PointType;
import com.bubbleshop.product.domain.constant.ProductImageCode;
import com.bubbleshop.product.interfaces.rest.dto.ModifyProductOptionReqDto;
import com.bubbleshop.product.interfaces.rest.dto.ModifyProductPointReqDto;
import com.bubbleshop.product.interfaces.rest.dto.ModifyProductReqDto;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, imports = {PointType.class})
public abstract class ModifyProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "options", source = "reqDto.options", qualifiedByName = "ModifyProductCommand.Set<ProductOption>"),
            @Mapping(target = "points", source = "reqDto.points", qualifiedByName = "ModifyProductCommand.Set<ProductPoint>"),
            @Mapping(target = "featureTypes", ignore = true)
    })
    public abstract ModifyProductCommand toCommand(String productCode, ModifyProductReqDto reqDto);

    @Named("ModifyProductCommand.Set<ProductOption>")
    public abstract ModifyProductCommand.ProductOption toOptionCommand(ModifyProductOptionReqDto reqDto);

    @Named("ModifyProductCommand.Set<ProductPoint>")
    @Mapping(target = "productType", expression = "java( PointType.find(reqDto.getPointTypeCode()) )")
    public abstract ModifyProductCommand.ProductPoint toPointCommand(ModifyProductPointReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget ModifyProductCommand.ModifyProductCommandBuilder builder,
            ModifyProductReqDto reqDto
    ) {
        if(!reqDto.getFeatures().isEmpty())
            builder.featureTypes(reqDto.getFeatures().stream().map(FeatureType::find).collect(Collectors.toSet()));
    }

}
