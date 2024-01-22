package com.iam.shoutz.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.properties")
@Getter
@Setter
public class AppConfig {
    private String defaultProfileImageUrl;
}
