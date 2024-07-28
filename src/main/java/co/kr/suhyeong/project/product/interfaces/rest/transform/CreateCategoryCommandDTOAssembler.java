package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.CreateCategoryCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyCategoryCommand;
import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateCategoryReqDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.ModifyCategoryReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, imports = { CategoryType.class })
public abstract class CreateCategoryCommandDTOAssembler {

    @Mapping(target = "categoryType", expression = "java( CategoryType.valueOf(reqDto.getCategoryType().toUpperCase()) )")
    @Mapping(target = "isShow", source = "show")
    public abstract CreateCategoryCommand toCommand(CreateCategoryReqDto reqDto);

    @Mapping(target = "categoryCode", source = "categoryCode")
    @Mapping(target = "categoryType", expression = "java( CategoryType.valueOf(reqDto.getCategoryType().toUpperCase()) )")
    @Mapping(target = "isShow", source = "reqDto.show")
    public abstract ModifyCategoryCommand toCommand(String categoryCode, ModifyCategoryReqDto reqDto);
}
