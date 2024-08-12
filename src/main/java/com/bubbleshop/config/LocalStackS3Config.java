package com.bubbleshop.config;

import com.bubbleshop.constants.StaticValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import java.net.URI;

@Configuration
@Profile(StaticValues.Profiles.localProfile)
public class LocalStackS3Config implements S3Config {
    @Value("${cloud.aws.s3.host}")
    private String s3Host;

    @Value("${cloud.aws.s3.access_key}")
    private String accessKey;

    @Value("${cloud.aws.s3.secret_key}")
    private String secretKey;

    @Value("${cloud.aws.s3.region}")
    private String region;

    // AmazonS3 = AWS SDK For Java V1
    // S3Client = AWS SDK For Java V2
    // software.amazon.awssdk:s3 를 추가했기 때문에 V2 S3Client 를 사용함
    @Bean
    @Override
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(s3Host)) // LocalStack 의 S3 엔드포인트
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .region(Region.of(region))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build();
    }
}
