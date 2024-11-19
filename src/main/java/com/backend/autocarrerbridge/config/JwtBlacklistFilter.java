package com.backend.autocarrerbridge.config;

import com.backend.autocarrerbridge.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@RequiredArgsConstructor
public class JwtBlacklistFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);


        if (token != null) {
            String idJwt = null;
            try {
                idJwt = tokenService.getClaim(token, "jti");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if (idJwt != null && redisTemplate.hasKey(idJwt)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
