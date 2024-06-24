package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.product.application.internal.commandservice.ProductCommandService;
import co.kr.suhyeong.project.product.application.internal.queryservice.ProductImageQueryService;
import co.kr.suhyeong.project.product.application.internal.queryservice.ProductQueryService;
import co.kr.suhyeong.project.product.domain.command.GetProductImageCommand;
import co.kr.suhyeong.project.product.domain.command.GetProductListCommand;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.EditProductReqDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductImageRspDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductListRspDto;
import co.kr.suhyeong.project.product.interfaces.rest.transform.CreateProductCommandDTOAssembler;
import co.kr.suhyeong.project.product.interfaces.rest.transform.GetProductImageCommandDTOAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static co.kr.suhyeong.project.product.interfaces.rest.controller.ProductUrl.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PRODUCT_DEFAULT_URL)
public class ProductController extends BaseController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;
    private final ProductImageQueryService productImageQueryService;

    private final CreateProductCommandDTOAssembler createProductCommandDTOAssembler;
    private final GetProductImageCommandDTOAssembler getProductImageCommandDTOAssembler;

    @PostMapping(PRODUCTS)
    public ResponseEntity<Void> createProduct(@RequestBody @Validated CreateProductReqDto reqDto) {
        productCommandService.createProduct(createProductCommandDTOAssembler.toCommand(reqDto));
        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @GetMapping(PRODUCTS)
    public ResponseEntity<List<Product>> getProductList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "0") Integer size) {
        GetProductListCommand command = GetProductListCommand.builder().pageable(PageRequest.of(page-1, size)).build();
        List<Product> productList = productQueryService.getProductList(command);

        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(productList);
    }

    @GetMapping(PRODUCT)
    public ResponseEntity<ProductView> getProduct(@PathVariable String productId) {
        ProductView product = productQueryService.getProduct(productId);
        return new ResponseEntity<>(product, getSuccessHeaders(), HttpStatus.OK);
    }

    @PutMapping(PRODUCT)
    public ResponseEntity<Void> editProduct(@PathVariable String productId, @RequestBody EditProductReqDto reqDto) {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @GetMapping(PRODUCT_IMAGE)
    public ResponseEntity<GetProductImageRspDto> getProductImageByProductCode(@PathVariable String productId, @RequestParam String divCode) {
        GetProductImageCommand command = getProductImageCommandDTOAssembler.toCommand(productId, divCode);
        List<ProductImage> list = productImageQueryService.getProductImages(command);
        GetProductImageRspDto rspDto = getProductImageCommandDTOAssembler.toRspDTO(list, productId);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(rspDto);
    }

}
