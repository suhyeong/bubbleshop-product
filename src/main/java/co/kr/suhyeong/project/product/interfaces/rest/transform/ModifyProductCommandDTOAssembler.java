package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;
import co.kr.suhyeong.project.product.domain.constant.OptionType;
import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;
import co.kr.suhyeong.project.product.interfaces.rest.dto.ModifyProductReqDto;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static co.kr.suhyeong.project.constants.StaticValues.COMMON_Y;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ModifyProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "options", ignore = true),
            @Mapping(target = "mainCategoryCode", ignore = true),
            @Mapping(target = "subCategoryCode", ignore = true),
            @Mapping(target = "isSale", ignore = true)
    })
    public abstract ModifyProductCommand toCommand(String productCode, ModifyProductReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget ModifyProductCommand.ModifyProductCommandBuilder<?,?> builder,
            ModifyProductReqDto reqDto
    ) {
        builder.mainCategoryCode(MainCategoryCode.find(reqDto.getMainCategoryCode()));
        builder.subCategoryCode(SubCategoryCode.find(reqDto.getSubCategoryCode()));
        builder.options(this.getOptionTypeList(reqDto.getOptions()));
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
