package co.kr.suhyeong.project.product.interfaces.rest.transform;

import co.kr.suhyeong.project.product.domain.command.GetProductImageCommand;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.model.view.ProductImageView;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductImageDetailRspDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductImageRspDto;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class GetProductImageCommandDTOAssembler {

    @Mappings({
            @Mapping(target = "productCode", source = "productId"),
            @Mapping(target = "productImageCodeList", ignore = true)
    })
    public abstract GetProductImageCommand toCommand(String productId, String divCode);

    @AfterMapping
    protected void afterMappingToCommand(@MappingTarget GetProductImageCommand.GetProductImageCommandBuilder builder,
                                         String productId, String divCode) {
        String[] divCodes = divCode.split(",");
        List<ProductImageCode> codeList = new ArrayList<>();
        for(String code : divCodes) {
            codeList.add(ProductImageCode.find(code));
        }
        builder.productImageCodeList(codeList);
    }

    @Mappings({
            @Mapping(target = "productId", source = "productId"),
            @Mapping(target = "details", source = "productImages", qualifiedByName = "GetProductImageRspDto.List<GetProductImageDetailRspDto>")
    })
    public abstract GetProductImageRspDto toRspDTO(List<ProductImageView> productImages, String productId);

    @Named(value = "GetProductImageRspDto.List<GetProductImageDetailRspDto>")
    @Mappings({
            @Mapping(target = "sequence", source = "imageSequence"),
            @Mapping(target = "divCode", source = "imageDivCode"),
            @Mapping(target = "path", source = "imagePath"),
            @Mapping(target = "fullUrl", source = "imageFullPath")
    })
    public abstract GetProductImageDetailRspDto toDetailRspDTO(ProductImageView productImage);
}
