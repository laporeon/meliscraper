package com.laporeon.meliscraper.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Meliscraper API")
                                .description("API for retrieving daily snapshots of Mercado Libre's best sellers.")
                                .version("1.0.0"));
    }
}
