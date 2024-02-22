package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;

    public void createProduct(CreateProductCommand command) {
        Product product = new Product(command);
        productRepository.save(product);
    }
}
