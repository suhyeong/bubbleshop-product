package co.kr.suhyeong.project.product.application.internal.commandservice;

import co.kr.suhyeong.project.product.domain.service.S3BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageCommandService {
    private final S3BucketService s3BucketService;

    public List<String> uploadProductTempImages(List<MultipartFile> files) {
        return s3BucketService.putTempImages(files);
    }

    public String uploadProductTempImage(MultipartFile file) {
        return s3BucketService.putTempImages(file);
    }
}
