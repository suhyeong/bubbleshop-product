package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.CreateProductCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.interfaces.rest.dto.CreateProductReqDto;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CreateProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "optionName", ignore = true),
            @Mapping(target = "defaultOptionName", source = "defaultOption"),
            @Mapping(target = "featureTypes", ignore = true)
    })
    public abstract CreateProductCommand toCommand(CreateProductReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget CreateProductCommand.CreateProductCommandBuilder<?,?> builder,
            CreateProductReqDto reqDto
    ) {
        builder.optionName(new HashSet<>(reqDto.getOptions()));
        builder.featureTypes(new HashSet<>());
        if(Objects.nonNull(reqDto.getFeatures()) && !reqDto.getFeatures().isEmpty())
            builder.featureTypes(reqDto.getFeatures().stream().map(FeatureType::find).collect(Collectors.toSet()));
    }
}
