package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import co.kr.suhyeong.project.product.domain.service.S3BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductEventCommandService {
    private final S3BucketService s3BucketService;

    private List<String> getProductImagesPath(Product product) {
        return product.getImages().stream().map(ProductImage::getImgPath).collect(Collectors.toList());
    }

    public void deleteProductTempImages(Product product) {
        List<String> imagePathList = this.getProductImagesPath(product);
        s3BucketService.deleteS3Images(StaticValues.S3_TEMP_FOLDER, imagePathList);
    }

    public void deleteProductImages(Product product) {
        List<String> imagePathList = this.getProductImagesPath(product);
        s3BucketService.deleteS3Images(product.getProductCode() + "/", imagePathList);
    }
}
