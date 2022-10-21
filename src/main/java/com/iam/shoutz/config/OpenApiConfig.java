package com.iam.shoutz.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Shoutz API")
                        .version("API v1: alpha")
                        .description("My custom description of Shoutz API.")
                        .termsOfService("Terms of service")
                        .summary("My summary")
                        .contact(new Contact()
                                .name("Shoutz")
                                .email("myemail@company.com")
                                .url("http://isaacafrifa.com/support")
                        ));

    }
}