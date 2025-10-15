package com.bubbleshop.product.domain.service;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.product.domain.model.aggregate.Product;
import com.bubbleshop.product.domain.model.entity.ProductImage;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static software.amazon.awssdk.core.sync.RequestBody.fromBytes;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3BucketService {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public String putTempImage(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        String randomName = UUID.randomUUID().toString();
        String key = String.format("%s/%s", StaticValues.S3_TEMP_FOLDER, randomName); //임시 폴더에 저장하기 위해 temp/ 붙여주기

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

            return randomName;
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
            this.deleteS3Images(String.format("%s/", StaticValues.S3_TEMP_FOLDER), uploadedKeys);
            throw e;
        }
    }

    /**
     * 상품 이미지를 임시 파일에서 상품 코드 파일로 복사한다.
     *
     * 1. temp/{파일명} 의 이미지를 {상품 코드}/{파일명} 으로 복사한다.
     * 2. 실패하였을 경우 이전에 복사에 성공한 이미지들을 전부 삭제한다.
     * @param productCode
     * @param imagesPath
     */
    public void moveProductImagesFromTemp(String productCode, List<String> imagesPath) {
        List<String> uploadedKeys = new ArrayList<>(); // 중간에 실패하였을 경우 롤백을 위한 Key 리스트

        try {
            imagesPath.forEach(imageName -> {
                String key = this.copyProductImageFromTemp(productCode, imageName);
                uploadedKeys.add(key);
            });
        } catch (Exception e) {
            this.deleteS3Images(productCode + "/", uploadedKeys);
            throw e;
        }
    }

    private String copyProductImageFromTemp(String productCode, String fileName) {
        String sourceKey = String.format("%s/%s", StaticValues.S3_TEMP_FOLDER, fileName);
        String destinationKey = String.format("%s/%s", productCode, fileName);
        CopyObjectRequest copyReq = CopyObjectRequest.builder()
                .sourceBucket(bucketName)
                .sourceKey(sourceKey)
                .destinationBucket(bucketName)
                .destinationKey(destinationKey)
                .build();

        CopyObjectResponse response = s3Client.copyObject(copyReq);

        if(!response.sdkHttpResponse().isSuccessful()) //성공이 아닐 경우 throw exception
            throw new ApiException(ResponseCode.S3_COPY_DATA_ERROR);

        return fileName;
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
