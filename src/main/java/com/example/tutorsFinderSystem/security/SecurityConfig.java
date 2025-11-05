package com.example.tutorsFinderSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // Bean PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cho phép API công khai
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // tắt CSRF để dễ test API
            .authorizeHttpRequests(auth -> auth
                // các API public không cần đăng nhập
                .requestMatchers(
                    "/auth/**",    // cho phép tất cả API auth (đăng ký, đăng nhập)
                    "/files/**",          // cho phép truy cập file ảnh/pdf
                    "/error"              // tránh lỗi 401 khi gặp /error
                ).permitAll()
                // tất cả request khác phải đăng nhập
                .anyRequest().authenticated()
            )
            // có thể tạm thời tắt form login để test API
            .formLogin(form -> form.disable())
            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}
