package com.backend.autocarrerbridge.service.impl;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;

import lombok.RequiredArgsConstructor;

import static com.backend.autocarrerbridge.util.Constant.JTI;
import static com.backend.autocarrerbridge.util.Constant.SUB;
import static com.backend.autocarrerbridge.util.Constant.TIME_TO_LIVE;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenService tokenService;
    private final RedisTemplate<String, Boolean> redisTemplate;
    private final UserAccountService userAccountService;


    @Override
    public AuthenticationResponse authenticate(UserAccount userAccounts) throws ParseException {
        String accessToken = tokenService.generateToken(userAccounts, 1);
        String refreshToken = userAccounts.getRefreshToken();
        //
        if (refreshToken == null || tokenService.getTimeToLive(refreshToken) < TIME_TO_LIVE) {
            refreshToken = tokenService.generateToken(userAccounts, 24 * 7);
        }
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout() throws ParseException {
        String token = tokenService.getJWT();
        String jti = tokenService.getClaim(token, JTI);
        redisTemplate.opsForValue().set(jti, true, tokenService.getTimeToLive(token), TimeUnit.MINUTES);
    }

    @Override
    public String getNewToken() throws ParseException {
        String token = tokenService.getJWT();
        String jti = tokenService.getClaim(token, JTI);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(jti))) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }
        logout();
        String sub = tokenService.getClaim(token, SUB);
        UserAccount userAccounts = userAccountService.getUserByUsername(sub);
        return tokenService.generateToken(userAccounts, 1);
    }

}
