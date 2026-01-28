package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.CreateProductCommand;
import com.bubbleshop.product.domain.command.CreateProductPointCommand;
import com.bubbleshop.product.domain.constant.FeatureType;
import com.bubbleshop.product.domain.constant.PointType;
import com.bubbleshop.product.interfaces.rest.dto.CreateProductReqDto;
import com.bubbleshop.product.interfaces.rest.dto.ModifyProductPointReqDto;
import com.bubbleshop.util.DateTimeUtils;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, imports = {PointType.class})
public abstract class CreateProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "optionName", ignore = true),
            @Mapping(target = "defaultOptionName", source = "defaultOption"),
            @Mapping(target = "featureTypes", ignore = true),
            @Mapping(target = "points", source = "reqDto.points", qualifiedByName = "CreateProductCommand.Set<ProductPoint>"),
            @Mapping(target = "displayStartDate", ignore = true),
            @Mapping(target = "displayEndDate", ignore = true),
    })
    public abstract CreateProductCommand toCommand(CreateProductReqDto reqDto);

    @Named("CreateProductCommand.Set<ProductPoint>")
    @Mapping(target = "productType", expression = "java( PointType.find(reqDto.getPointTypeCode()) )")
    public abstract CreateProductPointCommand toPointCommand(ModifyProductPointReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget CreateProductCommand.CreateProductCommandBuilder<?,?> builder,
            CreateProductReqDto reqDto
    ) {
        builder.optionName(new HashSet<>(reqDto.getOptions()));
        if(Objects.nonNull(reqDto.getFeatures()) && !reqDto.getFeatures().isEmpty()) {
            builder.featureTypes(reqDto.getFeatures().stream().map(FeatureType::find).collect(Collectors.toSet()));
        }
        builder.displayStartDate(DateTimeUtils.convertStringToLocalDateTime(reqDto.getDisplayStartDate()));
        builder.displayEndDate(DateTimeUtils.convertStringToLocalDateTime(reqDto.getDisplayEndDate()));
    }
}
