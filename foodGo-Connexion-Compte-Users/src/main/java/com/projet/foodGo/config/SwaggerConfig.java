package com.projet.foodGo.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api-useraccount-foodgo") // Nom du groupe
                .packagesToScan("com.projet.foodGo.controller")
                .build();
    }

}
