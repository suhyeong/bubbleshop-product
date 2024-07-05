package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.constant.MainCategoryCode;
import co.kr.suhyeong.project.product.domain.constant.SubCategoryCode;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import org.mapstruct.*;

import java.util.HashSet;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class CreateProductCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "optionName", ignore = true),
            @Mapping(target = "defaultOptionName", source = "defaultOption"),
            @Mapping(target = "mainCategoryCode", ignore = true),
            @Mapping(target = "subCategoryCode", ignore = true)
    })
    public abstract CreateProductCommand toCommand(CreateProductReqDto reqDto);

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget CreateProductCommand.CreateProductCommandBuilder<?,?> builder,
            CreateProductReqDto reqDto
    ) {
        builder.mainCategoryCode(MainCategoryCode.find(reqDto.getMainCategoryCode()));
        builder.subCategoryCode(SubCategoryCode.find(reqDto.getSubCategoryCode()));
        if(!reqDto.getOptions().isEmpty())
            builder.optionName(new HashSet<>(reqDto.getOptions()));
    }
}
