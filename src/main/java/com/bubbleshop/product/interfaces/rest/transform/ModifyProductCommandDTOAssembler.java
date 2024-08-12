package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.ModifyProductCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.interfaces.rest.dto.ModifyProductOptionReqDto;
import com.bubbleshop.product.interfaces.rest.dto.ModifyProductReqDto;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ModifyProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "options", source = "reqDto.options", qualifiedByName = "ModifyProductCommand.Set<ProductOption>"),
            @Mapping(target = "featureTypes", ignore = true)
    })
    public abstract ModifyProductCommand toCommand(String productCode, ModifyProductReqDto reqDto);

    @Named("ModifyProductCommand.Set<ProductOption>")
    @Mapping(target = "isDefaultOption", source = "defaultOption")
    public abstract ModifyProductCommand.ProductOption toOptionCommand(ModifyProductOptionReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget ModifyProductCommand.ModifyProductCommandBuilder builder,
            ModifyProductReqDto reqDto
    ) {
        if(!reqDto.getFeatures().isEmpty())
            builder.featureTypes(reqDto.getFeatures().stream().map(FeatureType::find).collect(Collectors.toSet()));
    }

}
