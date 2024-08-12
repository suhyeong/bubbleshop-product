package com.bubbleshop.product.application.internal.commandservice;

import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.product.domain.service.S3BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductImageEventCommandService {
    private final S3BucketService s3BucketService;

    /**
     * 상품 이미지 정보를 정리한다.
     * 1. 추가된 이미지가 있을 경우 복사된 임시 폴더의 이미지를 삭제한다.
     * 2. 삭제된 이미지가 있을 경우 실제 S3 Bucket 상품 코드 패키지 내의 이미지를 삭제한다.
     *
     * @param productCode 상품코드
     * @param images 수정된 상품 이미지 정보
     */
    public void cleanUpProductImageInfo(String productCode, Map<String, List<String>> images) {
        if(images.containsKey(StaticValues.ImageStatus.ADD)) {
            s3BucketService.deleteS3Images(StaticValues.S3_TEMP_FOLDER, images.get(StaticValues.ImageStatus.ADD));
        }
        if(images.containsKey(StaticValues.ImageStatus.DELETE)) {
            s3BucketService.deleteS3Images(productCode + "/", images.get(StaticValues.ImageStatus.DELETE));
        }
    }
}
