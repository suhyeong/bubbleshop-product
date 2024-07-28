package co.kr.suhyeong.project.product.domain.service;

import co.kr.suhyeong.project.constants.ResponseCode;
import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.exception.ApiException;
import co.kr.suhyeong.project.product.domain.model.aggregate.Product;
import co.kr.suhyeong.project.product.domain.model.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static co.kr.suhyeong.project.constants.ResponseCode.S3_COPY_DATA_ERROR;
import static software.amazon.awssdk.core.sync.RequestBody.fromBytes;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3BucketService {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public String putTempImage(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();

        String key = StaticValues.S3_TEMP_FOLDER + fileName; //임시 폴더에 저장하기 위해 temp/ 붙여주기

        // S3 에 업로드
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .contentLength(multipartFile.getSize())
                .build();

        try {
            PutObjectResponse response = s3Client.putObject(putObjectRequest, fromBytes(multipartFile.getBytes()));

            if(!response.sdkHttpResponse().isSuccessful()) //성공이 아닐 경우 throw exception
                throw new IOException();

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException(ResponseCode.S3_PUT_DATA_ERROR);
        }
    }

    public List<String> putTempImages(List<MultipartFile> multipartFileList) {
        List<String> uploadedKeys = new ArrayList<>(); // 중간에 실패하였을 경우 롤백을 위한 Key 리스트

        try {
            multipartFileList.forEach(multipartFile -> {
                String key = this.putTempImage(multipartFile);
                uploadedKeys.add(key);
            });
            return uploadedKeys;
        } catch (Exception e) {
            this.deleteS3Images(StaticValues.S3_TEMP_FOLDER, uploadedKeys);
            throw e;
        }
    }

    public void moveProductImagesFromTemp(Product product) {
        String productCode = product.getProductCode();
        List<String> imageNames = product.getImages().stream().map(ProductImage::getImgPath).collect(Collectors.toList());
        List<String> uploadedKeys = new ArrayList<>(); // 중간에 실패하였을 경우 롤백을 위한 Key 리스트

        try {
            imageNames.forEach(imageName -> {
                String key = this.moveProductImageFromTemp(productCode, imageName);
                uploadedKeys.add(key);
            });
        } catch (Exception e) {
            this.deleteS3Images(StaticValues.S3_TEMP_FOLDER, uploadedKeys);
            throw e;
        }
    }

    private String moveProductImageFromTemp(String productCode, String fileName) {
        String destinationKey = productCode + "/" + fileName;
        CopyObjectRequest copyReq = CopyObjectRequest.builder()
                .sourceBucket(bucketName)
                .sourceKey(StaticValues.S3_TEMP_FOLDER + fileName)
                .destinationBucket(bucketName)
                .destinationKey(destinationKey)
                .build();

        CopyObjectResponse response = s3Client.copyObject(copyReq);

        if(!response.sdkHttpResponse().isSuccessful()) //성공이 아닐 경우 throw exception
            throw new ApiException(S3_COPY_DATA_ERROR);

        return destinationKey;
    }

    public void deleteS3Images(String prefixPath, List<String> keys) {
        keys.forEach(key -> {
            try {
                s3Client.deleteObject(builder -> builder.bucket(bucketName).key(prefixPath + key).build());
            } catch (Exception e) {
                // 삭제 진행시 실패된 이미지는 건너뛰고 다음 이미지는 지울 수 있도록 try-catch 처리
                log.error("S3 이미지 삭제시 에러 발생. 삭제 실패 이미지 파일명 : {} , Exception : {}", key, e.getStackTrace());
            }
        });
    }
}
