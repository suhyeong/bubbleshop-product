package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static co.kr.suhyeong.project.constants.ResponseCode.NON_EXIST_DATA;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;

    @Transactional(rollbackFor = Exception.class)
    public void createProduct(CreateProductCommand command) {
        int count = productRepository.countByMainCategoryCodeAndSubCategoryCode(command.getMainCategoryCode(), command.getSubCategoryCode());
        Product product = new Product(command, count+1);
        productRepository.save(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyProduct(ModifyProductCommand command) {
        Product product = productRepository.findByProductCode(command.getProductCode())
                .orElseThrow(() -> new ApiException(NON_EXIST_DATA));
        product.modifyProduct(command);
        productRepository.save(product);
    }
}
