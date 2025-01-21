package com.bubbleshop.product.interfaces.rest.controller;

import com.bubbleshop.product.application.internal.queryservice.ProductQueryService;
import com.bubbleshop.product.domain.model.view.ProductView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.bubbleshop.product.interfaces.rest.controller.ProductUrl.PRODUCTS;
import static com.bubbleshop.product.interfaces.rest.controller.ProductUrl.PRODUCT_DEFAULT_URL;

@Tag(name = "Product API", description = "상품 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PRODUCT_DEFAULT_URL)
public class ProductController extends BaseController {
    private final ProductQueryService productQueryService;

    @Operation(summary = "상품 다건 조회 API", description = "상품 아이디로 상품 리스트를 조회한다.")
    @GetMapping(value = PRODUCTS)
    public ResponseEntity<Object> getProductList(@RequestParam List<String> productIds) {
        Map<String, ProductView> result = productQueryService.getProductList(productIds);
        return ResponseEntity.ok()
                .headers(getSuccessHeaders())
                .body(result);
    }
}
