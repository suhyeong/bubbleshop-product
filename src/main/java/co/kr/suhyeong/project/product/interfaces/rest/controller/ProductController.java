package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.product.application.internal.commandservice.ProductCommandService;
import co.kr.suhyeong.project.product.application.internal.queryservice.ProductQueryService;
import co.kr.suhyeong.project.product.domain.command.GetProductImageCommand;
import co.kr.suhyeong.project.product.domain.command.GetProductListCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.view.ProductImageView;
import co.kr.suhyeong.project.product.domain.model.view.ProductView;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.ModifyProductReqDto;
import co.kr.suhyeong.project.product.interfaces.rest.dto.GetProductImageRspDto;
import co.kr.suhyeong.project.product.interfaces.rest.transform.CreateProductCommandDTOAssembler;
import co.kr.suhyeong.project.product.interfaces.rest.transform.GetProductImageCommandDTOAssembler;
import co.kr.suhyeong.project.product.interfaces.rest.transform.ModifyProductCommandDTOAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.kr.suhyeong.project.product.interfaces.rest.controller.ProductUrl.*;

@Tag(name = "Product API", description = "상품 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PRODUCT_DEFAULT_URL)
public class ProductController extends BaseController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    private final CreateProductCommandDTOAssembler createProductCommandDTOAssembler;
    private final GetProductImageCommandDTOAssembler getProductImageCommandDTOAssembler;
    private final ModifyProductCommandDTOAssembler modifyProductCommandDTOAssembler;

    @Operation(summary = "상품 생성 API", description = "새로운 상품을 생성한다.")
    @ApiResponses(value = {
            @ApiResponse(description = "성공", responseCode = "200", headers = {
                    @Header(name = "resultCode", description = "응답 코드"),
                    @Header(name = "resultMessage", description = "응답 메세지")
            }),
            @ApiResponse(description = "요청값 Validation 실패", responseCode = "400")
    })
    @PostMapping(PRODUCTS)
    public ResponseEntity<Void> createProduct(@RequestBody @Validated CreateProductReqDto reqDto) {
        productCommandService.createProduct(createProductCommandDTOAssembler.toCommand(reqDto));
        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "상품 리스트 조회 API", description = "상품 리스트를 페이징 처리하여 조회한다.")
    @GetMapping(PRODUCTS)
    public ResponseEntity<List<Product>> getProductList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "0") Integer size) {
        GetProductListCommand command = new GetProductListCommand(page, size);
        List<Product> productList = productQueryService.getProductList(command);

        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(productList);
    }

    @Operation(summary = "단건 상품 조회 API", description = "상품 코드로 상품 정보를 조회한다.")
    @GetMapping(PRODUCT)
    public ResponseEntity<ProductView> getProduct(@PathVariable String productId) {
        ProductView product = productQueryService.getProduct(productId);
        return new ResponseEntity<>(product, getSuccessHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "상품 수정 API", description = "상품 코드로 기존 상품 정보를 수정한다.")
    @PutMapping(PRODUCT)
    public ResponseEntity<Void> modifyProduct(@PathVariable String productId, @RequestBody ModifyProductReqDto reqDto) {
        productCommandService.modifyProduct(modifyProductCommandDTOAssembler.toCommand(productId, reqDto));
        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @Operation(summary = "상품 이미지 정보 조회 API", description = "상품 코드로 상품 이미지 정보를 조회한다.")
    @GetMapping(PRODUCT_IMAGE)
    public ResponseEntity<GetProductImageRspDto> getProductImageByProductCode(@PathVariable String productId, @RequestParam String divCode) {
        GetProductImageCommand command = getProductImageCommandDTOAssembler.toCommand(productId, divCode);
        List<ProductImageView> list = productQueryService.getProductImages(command);
        GetProductImageRspDto rspDto = getProductImageCommandDTOAssembler.toRspDTO(list, productId);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(rspDto);
    }

}
