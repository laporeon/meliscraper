package com.laporeon.meliscraper.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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
                                .description("""
                                            API for retrieving daily snapshots of Mercado Libre's best sellers.
                                            
                                            ## Overview
                                            This API provides access to historical data of Mercado Libre's best-selling products,\s
                                            organized by categories and dates. Data is collected and stored as daily snapshots.
                                            
                                            ## Main Features
                                            - **Daily Snapshots**: Access current and historical best-seller data
                                            - **Category Management**: Browse and filter products by Mercado Libre categories
                                            - **Date-based Search**: Find snapshots from specific dates
                                            - **Product Listing**: View detailed product information by category
                                            - **Pagination & Sorting**: Navigate large datasets efficiently
                                            
                                            ## Rate Limiting
                                            Please be mindful of request frequency to ensure service availability.
                                            """)
                                .version("1.0.0")
                                .license(
                                        new License()
                                                .name("MIT")
                                                .url("https://opensource.org/licenses/MIT")
                                )
                );
    }
}
