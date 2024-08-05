package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.constant.FeatureType;
import co.kr.suhyeong.project.product.interfaces.rest.dto.ModifyProductOptionReqDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.ModifyProductReqDto;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.stream.Collectors;

import static co.kr.suhyeong.project.constants.StaticValues.COMMON_Y;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ModifyProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "options", source = "reqDto.options", qualifiedByName = "ModifyProductCommand.Set<ProductOption>"),
            @Mapping(target = "isSale", source = "reqDto.sale"),
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
