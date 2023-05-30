package co.kr.suhyeong.project.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@Import(TestPropertySourceConfig.class)
@EnableConfigurationProperties(value = ProductConfig.class)
class ProductConfigTest {
    @Autowired
    private ProductConfig productConfig;

    @Test
    void getProductConfig() {
        //given
        //when
        int limitCount = productConfig.getLimitCount();

        //then
        assertEquals(15, limitCount);
    }
}