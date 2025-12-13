package com.example.tutorsFinderSystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;

@Configuration
@Order(0)
public class WebSocketSecurityConfig {
    @PostConstruct
    public void checkLoaded() {
        System.out.println(">>> WebSocketSecurityConfig LOADED <<<");
    }

    @Bean
    public SecurityFilterChain webSocketSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/ws/**") // ⭐ CỰC KỲ QUAN TRỌNG
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll());

        return http.build();
    }
}
