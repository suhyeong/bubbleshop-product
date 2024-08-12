package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.constants.ResponseCode;
import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.command.ModifyProductImageCommand;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.event.ModifiedProductImageEvent;
import co.kr.suhyeong.project.product.domain.repository.ProductRepository;
import co.kr.suhyeong.project.product.domain.service.S3BucketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static co.kr.suhyeong.project.constants.StaticValues.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImageCommandService {
    private final ProductRepository productRepository;
    private final S3BucketService s3BucketService;

    private final ApplicationEventPublisher eventPublisher;

    public List<String> uploadProductTempImages(List<MultipartFile> files) {
        return s3BucketService.putTempImages(files);
    }

    public String uploadProductTempImage(MultipartFile file) {
        return s3BucketService.putTempImage(file);
    }

    /**
     * 상품의 이미지 정보를 수정한다.
     * 1. 상품 코드로 상품 Aggregate 를 조회한다.
     * 2. 상품 Aggregate 에서 상품 이미지 정보를 수정한다.
     * 3. 추가된 상품 이미지가 있을 경우 임시 폴더에서 이미지를 복사하여 이동시킨다.
     * 4. 상품 정보를 Save 후 내부 이벤트를 발행한다.
     *
     * @param command 이미지 정보 수정 Command
     */
    @CacheEvict(cacheNames = StaticValues.RedisKey.PRODUCT_KEY, key = "#command.productCode")
    @Transactional(rollbackFor = Exception.class)
    public void modifyProductImages(ModifyProductImageCommand command) {
        Product product = productRepository.findById(command.getProductCode())
                .orElseThrow(() -> new ApiException(ResponseCode.NON_EXIST_DATA));
        Map<String, List<String>> imgMap = product.modifyProductImages(command);

        if(imgMap.containsKey(ImageStatus.ADD)) {
            s3BucketService.moveProductImagesFromTemp(product.getProductCode(), imgMap.get(ImageStatus.ADD));
        }
        productRepository.save(product);
        eventPublisher.publishEvent(new ModifiedProductImageEvent(product, imgMap));
    }
}
