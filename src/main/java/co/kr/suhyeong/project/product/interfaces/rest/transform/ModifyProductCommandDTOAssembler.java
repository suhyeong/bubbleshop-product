package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.constant.FeatureType;
import co.kr.suhyeong.project.product.interfaces.rest.dto.ModifyProductReqDto;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.stream.Collectors;

import static co.kr.suhyeong.project.constants.StaticValues.COMMON_Y;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ModifyProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "optionName", ignore = true),
            @Mapping(target = "defaultOptionName", source = "reqDto.defaultOption"),
            @Mapping(target = "isSale", ignore = true),
            @Mapping(target = "featureTypes", ignore = true)
    })
    public abstract ModifyProductCommand toCommand(String productCode, ModifyProductReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget ModifyProductCommand.ModifyProductCommandBuilder<?,?> builder,
            ModifyProductReqDto reqDto
    ) {
        if(!reqDto.getOptions().isEmpty())
            builder.optionName(new HashSet<>(reqDto.getOptions()));
        builder.isSale(this.getSaleBoolean(reqDto.getSaleYn()));
        if(!reqDto.getFeatures().isEmpty())
            builder.featureTypes(reqDto.getFeatures().stream().map(FeatureType::find).collect(Collectors.toSet()));
    }

    private boolean getSaleBoolean(String saleYn) {
        return saleYn.equals(COMMON_Y);
    }
}
