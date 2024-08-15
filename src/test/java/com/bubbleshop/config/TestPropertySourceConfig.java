package com.bubbleshop.config;

import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {
        "classpath:properties/product/product-${spring.profiles.active}.yaml"
}, factory = YamlPropertySourceFactory.class)
public class TestPropertySourceConfig {
}