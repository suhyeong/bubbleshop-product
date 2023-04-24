package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductImageDetailRspDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductImageRspDto;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class GetProductImageCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "productId", source = "productId"),
            @Mapping(target = "details", ignore = true)
    })
    public abstract GetProductImageRspDto toRspDTO(List<ProductImage> productImages, String productId);

    @AfterMapping
    protected void afterMappingToRspDTO(@MappingTarget GetProductImageRspDto.GetProductImageRspDtoBuilder builder,
                                        List<ProductImage> productImages, String productId) {
        List<GetProductImageDetailRspDto> list = new ArrayList<>();
        productImages.forEach(image -> list.add(toRspDetailDTO(image)));
        builder.details(list);
    }

    @Mappings({
            @Mapping(target = "divCode", source = "productImage.divCode.code"),
            @Mapping(target = "path", source = "productImage.imgPath")
    })
    abstract GetProductImageDetailRspDto toRspDetailDTO(ProductImage productImage);
}
