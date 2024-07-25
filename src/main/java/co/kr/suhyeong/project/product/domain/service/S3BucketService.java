package co.kr.suhyeong.project.product.domain.service;

import co.kr.suhyeong.project.constants.ResponseCode;
import co.kr.suhyeong.project.constants.StaticValues;
import co.kr.suhyeong.project.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static software.amazon.awssdk.core.sync.RequestBody.fromBytes;

@Component
@RequiredArgsConstructor
public class S3BucketService {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public String putTempImages(MultipartFile multipartFile) {
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
                String key = this.putTempImages(multipartFile);
                uploadedKeys.add(key);
            });
            return uploadedKeys;
        } catch (Exception e) {
            uploadedKeys.forEach(this::deleteImage);
            throw e;
        }
    }

    public void deleteImage(String fileName) {
        try {
            s3Client.deleteObject(builder -> builder.bucket(bucketName).key(fileName).build());
        } catch (Exception e) {
            //todo
        }
    }
}
