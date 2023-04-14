package com.springsecure.test1.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationConfig {
    private String secretKey;
    public String getSecretKey() {
    return secretKey;
    }
}
