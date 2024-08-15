package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.CreateCategoryCommand;
import com.bubbleshop.product.domain.command.ModifyCategoryCommand;
import com.bubbleshop.product.domain.constant.CategoryType;
import com.bubbleshop.product.interfaces.rest.dto.CreateCategoryReqDto;
import com.bubbleshop.product.interfaces.rest.dto.ModifyCategoryReqDto;
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
