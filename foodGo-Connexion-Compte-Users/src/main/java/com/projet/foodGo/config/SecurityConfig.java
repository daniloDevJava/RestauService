package com.projet.foodGo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                .csrf(AbstractHttpConfigurer::disable) // Désactiver CSRF pour les tests dans Swagger (attention en production)
                .authorizeHttpRequests(auth -> auth
                        // Autoriser les endpoints spécifiques
                        .requestMatchers("/user-management/**").permitAll()
                        // Tous les autres endpoints nécessitent une authentification
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}

