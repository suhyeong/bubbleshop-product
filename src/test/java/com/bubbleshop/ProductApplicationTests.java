package com.bubbleshop;

import com.bubbleshop.config.S3BucketTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@Import(S3BucketTestConfig.class)
class ProductApplicationTests {

	@Test
	void contextLoads() {
	}

}
