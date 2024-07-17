package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.constant.OptionType;
import co.kr.suhyeong.project.product.interfaces.rest.dto.ModifyProductReqDto;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static co.kr.suhyeong.project.constants.StaticValues.COMMON_Y;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ModifyProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "optionName", ignore = true),
            @Mapping(target = "defaultOptionName", source = "reqDto.defaultOption"),
            @Mapping(target = "isSale", ignore = true)
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
    }

    private Set<OptionType> getOptionTypeList(List<String> options) {
        Set<OptionType> optionTypeSet = new HashSet<>();
        for(String option : options)
            optionTypeSet.add(OptionType.find(option));
        return optionTypeSet;
    }

    private boolean getSaleBoolean(String saleYn) {
        return saleYn.equals(COMMON_Y);
    }
}
