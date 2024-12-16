package com.foodGo.apiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Autoriser les origines (ici, frontend sur le même serveur que la Gateway)
        config.addAllowedOriginPattern("*"); // Utiliser * ou un domaine spécifique comme "http://localhost:6000"

        // Autoriser les méthodes HTTP
        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE, etc.

        // Autoriser les en-têtes HTTP
        config.addAllowedHeader("*");

        // Autoriser les informations d'identification (cookies, etc.)
        config.setAllowCredentials(true);

        // Appliquer la configuration à toutes les URL
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}

