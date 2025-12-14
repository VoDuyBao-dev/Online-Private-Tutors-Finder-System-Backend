package com.example.tutorsFinderSystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(0)
public class WebSocketSecurityConfig {

    @Bean
    public SecurityFilterChain webSocketChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/ws/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}
