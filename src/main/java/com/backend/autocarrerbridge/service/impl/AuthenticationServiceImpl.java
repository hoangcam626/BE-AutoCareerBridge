package com.backend.autocarrerbridge.service.impl;


import com.backend.autocarrerbridge.entity.UserAccount;

import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.model.api.IntrospectRequest;
import com.backend.autocarrerbridge.model.api.IntrospectResponse;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final TokenService tokenService;
    private final RedisTemplate<String, Boolean> redisTemplate;
    private final Integer TIME_TO_LIVE = 1440;
    private final UserAccountService userAccountService;
    @Override
    public AuthenticationResponse  authenticate(UserAccount userAccounts) throws ParseException {
        String accessToken = tokenService.generateToken(userAccounts, 1);
        String refreshToken = userAccounts.getRefreshToken();
        if (refreshToken == null || tokenService.getTimeToLive(refreshToken) < TIME_TO_LIVE) {
            refreshToken = tokenService.generateToken(userAccounts, 24 * 7);
        }
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(String token) throws ParseException {
        String jti = tokenService.getClaim(token, "jti");
        redisTemplate.opsForValue().set(jti, true, tokenService.getTimeToLive(token), TimeUnit.MINUTES);
    }

    @Override
    public String getNewToken(String token) throws ParseException {
        String jti = tokenService.getClaim(token, "jti");
        if(redisTemplate.hasKey(jti)){
            throw new RuntimeException("Token in destroy");
        }
        logout(token);
        String user_name  = tokenService.getClaim(token, "sub");
        UserAccount userAccounts = userAccountService.getUserByUsername(user_name);
        return tokenService.generateToken(userAccounts, 1);
    }


    @Override
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws Exception {
        String token = introspectRequest.getToken();
        boolean valid = tokenService.verifyToken(token) &&
                tokenService.getTimeToLive(token) > 0;
        return IntrospectResponse.builder().valid(valid).build();
    }
}

