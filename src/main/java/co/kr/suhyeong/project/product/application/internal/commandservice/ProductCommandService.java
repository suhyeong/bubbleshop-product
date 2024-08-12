package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.CreateProductCommand;
import co.kr.suhyeong.project.product.domain.command.ModifyProductCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.event.CreatedProductEvent;
import co.kr.suhyeong.project.product.domain.model.event.DeletedProductEvent;
import co.kr.suhyeong.project.product.domain.repository.CategoryRepository;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import co.kr.suhyeong.project.product.domain.service.S3BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static co.kr.suhyeong.project.constants.ResponseCode.INVALID_CATEGORY_TYPE;
import static co.kr.suhyeong.project.constants.ResponseCode.NON_EXIST_DATA;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final S3BucketService s3BucketService;

    private final ApplicationEventPublisher eventPublisher;

    private void checkCategory(String mainCategoryCode, String subCategoryCode) {
        if(!categoryRepository.existsById(mainCategoryCode) || !categoryRepository.existsById(subCategoryCode))
            throw new ApiException(INVALID_CATEGORY_TYPE);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProduct(CreateProductCommand command) {
        this.checkCategory(command.getMainCategoryCode(), command.getSubCategoryCode());
        int count = productRepository.countByMainCategoryCodeAndSubCategoryCode(command.getMainCategoryCode(), command.getSubCategoryCode());
        Product product = new Product(command, count+1);
        s3BucketService.moveProductImagesFromTemp(product);
        productRepository.save(product);
        eventPublisher.publishEvent(new CreatedProductEvent(product));
    }

    /**
     * 상품 정보 수정
     * @param command 상품 수정 Command
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = StaticValues.RedisKey.PRODUCT_KEY, key = "#command.productCode")
    public void modifyProduct(ModifyProductCommand command) {
        Product product = productRepository.findById(command.getProductCode())
                .orElseThrow(() -> new ApiException(NON_EXIST_DATA));
        product.modifyProduct(command);
        productRepository.save(product);
    }

    /**
     * 상품 정보 삭제
     * @param productCode 상품 코드
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = StaticValues.RedisKey.PRODUCT_KEY, key = "#productCode")
    public void deleteProduct(String productCode) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new ApiException(NON_EXIST_DATA));
        productRepository.delete(product);
        eventPublisher.publishEvent(new DeletedProductEvent(product));
    }
}
