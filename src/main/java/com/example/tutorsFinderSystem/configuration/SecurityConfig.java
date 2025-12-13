package com.example.tutorsFinderSystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    // Bean PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    private final String[] COMMON_URLS = {
            "/tutors/tutorDetail/**",


    };

    // Cho phép API công khai
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // các API public không cần đăng nhập
                .requestMatchers(
                    "/auth/**",    // cho phép tất cả API auth (đăng ký, đăng nhập)
                    "/files/**",          // cho phép truy cập file ảnh/pdf
                    "/error",              // tránh lỗi 401 khi gặp /error
                    "/drive/view/**",
                    "/classes/**"
                ).permitAll()
                .requestMatchers(COMMON_URLS).hasAnyAuthority("SCOPE_TUTOR", "SCOPE_LEARNER")
                .requestMatchers("/tutors/**").hasAuthority("SCOPE_TUTOR")
                .requestMatchers("/admin/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers("/learner/**").hasAuthority("SCOPE_LEARNER")


                    // tất cả request khác phải đăng nhập
                .anyRequest().authenticated()
            )
//                xử lý xác thực jwt
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.decoder(customJwtDecoder))
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
//                xử lý khi người dùng chưa xác thực được
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
            )

            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable()); // tắt CSRF để dễ test API



        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
