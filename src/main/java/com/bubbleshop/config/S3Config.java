package com.bubbleshop.config;

import software.amazon.awssdk.services.s3.S3Client;

public interface S3Config {
    public S3Client s3Client();
}
