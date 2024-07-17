package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.GetCategoriesCommand;
import co.kr.suhyeong.project.product.domain.constant.CategoryType;
import org.mapstruct.*;
import org.springframework.data.domain.PageRequest;

import static co.kr.suhyeong.project.constants.StaticValues.COMMON_Y;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class GetCategoriesCommandDTOAssembler {

    @Mapping(target = "categoryType", ignore = true)
    public abstract GetCategoriesCommand toCommand(
            Integer page, Integer size, String pagingYn, String categoryType, String categoryCode
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
}
