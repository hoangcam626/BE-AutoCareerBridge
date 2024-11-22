package com.backend.autocarrerbridge.service.impl;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
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
    private final UserAccountService userAccountService;
    private static final int DEFAULT_TOKEN_LIFETIME_MINUTES = 1440; // 24 giờ
    private static final int REFRESH_TOKEN_EXPIRATION_HOURS = 24 * 7; // 7 ngày

    @Override
    public AuthenticationResponse  authenticate(UserAccount userAccounts) throws ParseException {
        String accessToken = tokenService.generateToken(userAccounts, 1);
        String refreshToken = userAccounts.getRefreshToken();
        if (refreshToken == null || tokenService.getTimeToLive(refreshToken) < REFRESH_TOKEN_EXPIRATION_HOURS) {
            refreshToken = tokenService.generateToken(userAccounts, DEFAULT_TOKEN_LIFETIME_MINUTES);
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
        if(Boolean.TRUE.equals(redisTemplate.hasKey(jti))){
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }
        logout(token);
        UserAccount userAccounts = userAccountService.getUserByUsername(tokenService.getClaim(token, "sub"));
        return tokenService.generateToken(userAccounts, 1);
    }

}

