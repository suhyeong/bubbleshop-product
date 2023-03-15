package co.kr.suhyeong.project.product.interfaces.rest.controller;

import co.kr.suhyeong.project.product.interfaces.rest.dto.CreateProductReqDto;
import co.kr.suhyeong.project.product.interfaces.rest.validator.CreateProductReqDtoValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static co.kr.suhyeong.project.product.interfaces.rest.controller.ProductUrl.PRODUCTS;
import static co.kr.suhyeong.project.product.interfaces.rest.controller.ProductUrl.PRODUCT_DEFAULT_URL;

@Slf4j
@RestController
@RequestMapping(value = PRODUCT_DEFAULT_URL)
public class ProductController extends BaseController {

    @PostMapping(PRODUCTS)
    public ResponseEntity<Void> createProduct(@RequestBody @Validated CreateProductReqDto reqDto) {

        return new ResponseEntity<>(null, getSuccessHeaders(), HttpStatus.OK);
    }
}
