package com.bubbleshop.product.infrastructure.feignclient;

import com.bubbleshop.config.S3BucketTestConfig;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.product.infrastructure.dto.GetMemberRspDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@Import(S3BucketTestConfig.class)
@ActiveProfiles("unit-test")
class MemberClientTest {

    @Autowired
    private MemberClient memberClient;

    @Test
    @DisplayName("회원 정보 조회 - 성공")
    void getMember_success() {
        GetMemberRspDTO response = memberClient.getMember("123");

        assertEquals("홍길동", response.getMemberName());
    }

    @Test
    @DisplayName("회원 정보 조회 - 회원 정보가 없어 실패")
    void getMember_fail_non_found_member() {
        ApiException exception = assertThrows(ApiException.class, () -> memberClient.getMember("000"));

        assertEquals(ResponseCode.NON_EXIST_DATA.getResponseCode(), exception.getResultCode());
    }
}