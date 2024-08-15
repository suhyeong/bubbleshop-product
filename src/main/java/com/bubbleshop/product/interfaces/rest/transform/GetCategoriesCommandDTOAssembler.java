package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.GetCategoriesCommand;
import com.bubbleshop.product.domain.constant.CategoryType;
import com.bubbleshop.product.domain.model.view.CategoryListView;
import com.bubbleshop.product.domain.model.view.CategoryView;
import com.bubbleshop.product.interfaces.rest.dto.GetCategoriesRspDto;
import com.bubbleshop.product.interfaces.rest.dto.GetCategoryRspDto;
import com.bubbleshop.constants.StaticValues;
import org.mapstruct.*;
import org.springframework.data.domain.PageRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class GetCategoriesCommandDTOAssembler {

    @Mapping(target = "categoryType", ignore = true)
    public abstract GetCategoriesCommand toCommand(
            Integer page, Integer size, String pagingYn, String categoryType, String categoryCode, String categoryName, boolean isCategoryNameContains
    );

    @AfterMapping
    protected void afterMappingToCommand(
            @MappingTarget GetCategoriesCommand.GetCategoriesCommandBuilder builder,
            Integer page, Integer size, String pagingYn, String categoryType, String categoryCode
    ) {
        boolean isNeedToPaging = pagingYn.equals(StaticValues.COMMON_Y);
        builder.isNeedToPaging(isNeedToPaging);
        if(isNeedToPaging)
            builder.paging(PageRequest.of(page-1, size));
        if(!categoryType.isBlank())
            builder.categoryType(CategoryType.valueOf(categoryType.toUpperCase()));
    }

    @Mappings({
            @Mapping(target = "totalCount", source = "view.count"),
            @Mapping(target = "categoryList", source = "categoryList", qualifiedByName = "GetCategoriesRspDto.GetCategoryRspDto")
    })
    public abstract GetCategoriesRspDto toRspDto(CategoryListView view);

    @Named("GetCategoriesRspDto.GetCategoryRspDto")
    @Mapping(target = "categoryType", expression = "java( view.getCategoryType().name().toLowerCase() )")
    public abstract GetCategoryRspDto toCateRspDto(CategoryView view);
}
