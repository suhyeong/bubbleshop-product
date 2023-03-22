package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.kr.suhyeong.project.product.interfaces.rest.controller.ProductUrl.*;

@Slf4j
@RestController
@RequestMapping(value = PRODUCT_DEFAULT_URL)
public class ProductController extends BaseController {

    @PostMapping(PRODUCTS)
    public ResponseEntity<Void> createProduct(@RequestBody @Validated CreateProductReqDto reqDto) {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @GetMapping(PRODUCTS)
    public ResponseEntity<Void> getProductList() {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }

    @GetMapping(PRODUCT)
    public ResponseEntity<Void> getProduct(@PathVariable String productId) {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }
}
