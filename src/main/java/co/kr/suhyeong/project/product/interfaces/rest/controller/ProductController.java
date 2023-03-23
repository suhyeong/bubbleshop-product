package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.product.application.internal.commandservice.ProductCommandService;
import co.kr.suhyeong.project.product.application.internal.queryservice.ProductQueryService;
import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.kr.suhyeong.project.product.interfaces.rest.controller.ProductUrl.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PRODUCT_DEFAULT_URL)
public class ProductController extends BaseController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @PostMapping(PRODUCTS)
    public ResponseEntity<Void> createProduct(@RequestBody @Validated CreateProductReqDto reqDto) {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @GetMapping(PRODUCTS)
    public ResponseEntity<Void> getProductList(@RequestParam(value = "0") Integer page,
                                               @RequestParam(value = "0") Integer size) {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @GetMapping(PRODUCT)
    public ResponseEntity<Void> getProduct(@PathVariable String productId) {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }
}
