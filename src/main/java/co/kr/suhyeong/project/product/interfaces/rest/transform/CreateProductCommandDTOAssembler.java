package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;
import co.kr.suhyeong.project.product.domain.constant.OptionType;
import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CreateProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "options", ignore = true),
            @Mapping(target = "mainCategoryCode", ignore = true),
            @Mapping(target = "subCategoryCode", ignore = true)
    })
    public abstract CreateProductCommand toCommand(CreateProductReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget CreateProductCommand.CreateProductCommandBuilder builder,
            CreateProductReqDto reqDto
    ) {
        builder.mainCategoryCode(MainCategoryCode.find(reqDto.getMainCategoryCode()));
        builder.subCategoryCode(SubCategoryCode.find(reqDto.getSubCategoryCode()));
        builder.options(this.getOptionTypeList(reqDto.getOptions()));
    }

    private Set<OptionType> getOptionTypeList(List<String> options) {
        Set<OptionType> optionTypeSet = new HashSet<>();
        for(String option : options)
            optionTypeSet.add(OptionType.find(option));
        return optionTypeSet;
    }
}
