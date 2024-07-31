package co.kr.suhyeong.project.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "product")
public class ProductConfig {
    private int limitCount;
    private String imageUrl;
}
