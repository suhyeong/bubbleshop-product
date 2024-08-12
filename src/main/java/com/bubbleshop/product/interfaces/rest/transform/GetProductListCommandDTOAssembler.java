package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.GetProductListCommand;
import com.bubbleshop.product.domain.model.view.ProductListView;
import com.bubbleshop.product.domain.model.view.ProductView;
import com.bubbleshop.product.interfaces.rest.dto.GetProductListRspDto;
import com.bubbleshop.product.interfaces.rest.dto.GetProductRspDto;
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
            @Mapping(target = "totalCount", source = "productListView.count"),
            @Mapping(target = "productList", source = "productList", qualifiedByName = "GetProductListRspDto.GetProductRspDto")
    })
    public abstract GetProductListRspDto toRspDto(ProductListView productListView);

    @Named("GetProductListRspDto.GetProductRspDto")
    @Mappings({
            @Mapping(target = "imageList", ignore = true),
            @Mapping(target = "features", ignore = true),
            @Mapping(target = "options", ignore = true)
    })
    public abstract GetProductRspDto toPrdRspDto(ProductView view);
}
