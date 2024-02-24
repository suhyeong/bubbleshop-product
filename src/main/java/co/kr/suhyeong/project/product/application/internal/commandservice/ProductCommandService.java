package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.repository.ProductImageRepository;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public void createProduct(CreateProductCommand command) {
        Product product = new Product(command);
        ProductImage productImage = new ProductImage(product, command);
        productRepository.save(product);
        productImageRepository.save(productImage);
    }
}
