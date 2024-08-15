package com.bubbleshop.product.interfaces.rest.transform;

import com.bubbleshop.product.domain.command.ModifyProductImageCommand;
import com.bubbleshop.product.domain.constant.ProductImageCode;
import com.bubbleshop.product.interfaces.rest.dto.ModifyProductImageReqDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        imports = {ProductImageCode.class})
public abstract class ModifyProductImageCommandDTOAssembler {

    @Mapping(target = "images", source = "reqDto.images", qualifiedByName = "ModifyProductImageCommand.List<ProductImage>")
    public abstract ModifyProductImageCommand toCommand(String productCode, ModifyProductImageReqDto reqDto);

    @Named("ModifyProductImageCommand.List<ProductImage>")
    @Mappings({
            @Mapping(target = "imageDivCode", expression = "java( ProductImageCode.find(reqDto.getDivCode()) )"),
            @Mapping(target = "path", source = "fileName")
    })
    public abstract ModifyProductImageCommand.ProductImage toCommand(ModifyProductImageReqDto.ImageReqDto reqDto);
}
