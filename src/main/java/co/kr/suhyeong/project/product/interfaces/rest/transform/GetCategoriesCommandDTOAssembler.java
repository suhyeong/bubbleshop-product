package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import co.kr.suhyeong.project.product.domain.model.view.CategoryListView;
import co.kr.suhyeong.project.product.domain.model.view.CategoryView;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetCategoriesRspDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetCategoryRspDto;
import org.mapstruct.*;
import org.springframework.data.domain.PageRequest;

import static co.kr.suhyeong.project.constants.StaticValues.COMMON_Y;

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
        boolean isNeedToPaging = pagingYn.equals(COMMON_Y);
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
