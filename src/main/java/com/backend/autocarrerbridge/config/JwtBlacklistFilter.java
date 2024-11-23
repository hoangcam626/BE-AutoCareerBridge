package com.backend.autocarrerbridge.config;

import com.backend.autocarrerbridge.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AccessLevel;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

import static com.backend.autocarrerbridge.util.Constant.JTI;
import static com.backend.autocarrerbridge.util.Constant.TOKEN_BLACKLIST;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtBlacklistFilter extends OncePerRequestFilter {

    RedisTemplate<String, Object> redisTemplate;
    TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest  request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);


        if (token != null) {
            String idJwt;
            try {
                idJwt = tokenService.getClaim(token, JTI);
            } catch (ParseException e) {
                throw new ServletException(e);
            }
            if (idJwt != null && Boolean.TRUE.equals(redisTemplate.hasKey(idJwt))) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, TOKEN_BLACKLIST);
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
