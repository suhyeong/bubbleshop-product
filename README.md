# Product 상품 도메인 프로젝트
상품 (Product) 도메인

# Getting Started
Spring Boot Version 2.7.3

### 1. Init Setting
현재 로컬 구현 가능 서비스 (2024/07/24)
Mysql(v5.7) Redis(v7.2.5) AWS S3(v3.5.0)

#### 1. Makefile 을 통해 선언한 DOCKER 명령어로 도커 컨테이너 생성
```
make container-up
```
#### 2. resources > db > container > schema_initialize.sql 수행
![schema_initialize](https://github.com/user-attachments/assets/6f7e183d-50bf-47a0-a752-c1935062464b)
#### 3. resources > db > container > create-bucket.sh 수행
![create-bucket](https://github.com/user-attachments/assets/932c9aa8-984f-41ae-b433-a8b8a586785c)


<details>
<summary>Reference Documentation</summary>

### For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
</details>