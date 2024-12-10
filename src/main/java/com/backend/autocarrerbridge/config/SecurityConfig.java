package com.backend.autocarrerbridge.config;

import java.util.List;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.backend.autocarrerbridge.service.TokenService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.signerKey}")
    private String signerKey;

    private final RedisTemplate<String, Object> redisTemplate;
    private final TokenService tokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtBlacklistFilter jwtBlacklistFilter() {
        return new JwtBlacklistFilter(redisTemplate, tokenService);
    }

    private static final String[] AUTH_WHITELIST = {
        "/api/business/register",
        "/api/university/register",
        "/api/accounts/refresh",
        "/api/accounts/login",
        "/api/accounts/verify",
            "/api/business/verify-business",
            "/api/university/verify-university",
        "/api/accounts/forgot-code",
        "/api/accounts/forgot-pass",
        "/api/accounts/jwt-introspect",
        "/api/v1/image/**",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/webjars/**",
        "/api/administrative/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtBlacklistFilter jwtBlacklistFilter)
            throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Vô hiệu hóa CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, AUTH_WHITELIST).permitAll() // Cho phép các POST request trong AUTH_WHITELIST
                        .requestMatchers(AUTH_WHITELIST).permitAll() // Cho phép các request trong AUTH_WHITELIST
                        .anyRequest().authenticated() // Yêu cầu xác thực cho các request khác
                )
                .addFilterBefore(
                        jwtBlacklistFilter, UsernamePasswordAuthenticationFilter.class // Thêm JWT Filter trước xử lý UsernamePasswordAuthentication
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))) // Cấu hình JWT decoder
                .build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTION"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
