package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.constant.ProductImageCode;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.repository.ProductImageRepository;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional(rollbackFor = {Exception.class})
    public void createProduct(CreateProductCommand command) {
        Product product = new Product(command);
        productRepository.save(product);
        this.createProductImage(product, command);
    }

    private void createProductImage(Product product, CreateProductCommand command) {
        if(command.isThumbnailImageExist())
            productImageRepository.save(new ProductImage(product, ProductImageCode.THUMBNAIL_IMAGE, command.getThumbnailImagePath()));
        if(command.isDetailImageExist())
            productImageRepository.save(new ProductImage(product, ProductImageCode.FULL_DETAIL_IMAGE, command.getDetailImagePath()));
    }
}
