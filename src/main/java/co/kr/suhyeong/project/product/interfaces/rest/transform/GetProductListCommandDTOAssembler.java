package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.GetProductListCommand;
import co.kr.suhyeong.project.product.domain.model.view.ProductListView;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductListRspDto;
import org.mapstruct.*;
import org.springframework.data.domain.PageRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class GetProductListCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "pageable", ignore = true),
            @Mapping(target = "isNameContains", source = "isProductNameContains")
    })
    public abstract GetProductListCommand toCommand(Integer page, Integer size,
                                                    String productCode, String productName, boolean isProductNameContains,
                                                    String mainCategoryCode, String subCategoryCode);

    @AfterMapping
    protected void afterMappingToCommand(@MappingTarget GetProductListCommand.GetProductListCommandBuilder builder,
                                         Integer page, Integer size,
                                         String productCode, String productName, boolean isProductNameContains,
                                         String mainCategoryCode, String subCategoryCode) {
        builder.pageable(PageRequest.of(page-1, size));
    }

    @Mappings({
            @Mapping(target = "totalCount", source = "productListView.count")
    })
    public abstract GetProductListRspDto toRspDto(ProductListView productListView);
}
