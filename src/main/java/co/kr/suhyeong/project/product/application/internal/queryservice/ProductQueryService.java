package co.kr.suhyeong.project.product.application.internal.queryservice;

import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.GetProductListCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static co.kr.suhyeong.project.constants.ResponseCode.NON_EXIST_DATA;
import static co.kr.suhyeong.project.constants.ResponseCode.SERVER_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {
    private final ProductRepository productRepository;

    public Product getProduct(String productCode) {
        return productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new ApiException(NON_EXIST_DATA));
    }

    public List<Product> getProductList(GetProductListCommand command) {
        try {
            return productRepository.findProductListWithPagination(command.getPageable());
        } catch (Exception e) {
            throw new ApiException(SERVER_ERROR);
        }
    }
}
