package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.util.Constant.JTI;
import static com.backend.autocarrerbridge.util.Constant.SUB;
import static com.backend.autocarrerbridge.util.Constant.TIME_TO_LIVE;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;

import lombok.RequiredArgsConstructor;

/**
 * Lớp triển khai AuthenticationService.
 * Quản lý việc xác thực người dùng, tạo token, và quản lý phiên làm việc.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenService tokenService;
    private final RedisTemplate<String, Boolean> redisTemplate;
    private final UserAccountService userAccountService;

    /**
     * Xác thực người dùng và tạo access token và refresh token.
     *
     * @param userAccounts Tài khoản người dùng cần xác thực.
     * @return AuthenticationResponse chứa access token và refresh token.
     * @throws ParseException Nếu xảy ra lỗi trong quá trình xử lý token.
     */
    @Override
    public AuthenticationResponse authenticate(UserAccount userAccounts) throws ParseException {
        // Tạo access token ngắn hạn (1 giờ).
        String accessToken = tokenService.generateToken(userAccounts, 1);

        // Lấy hoặc tạo refresh token dài hạn (7 ngày).
        String refreshToken = userAccounts.getRefreshToken();
        if (refreshToken == null || tokenService.getTimeToLive(refreshToken) < TIME_TO_LIVE) {
            refreshToken = tokenService.generateToken(userAccounts, 24 * 7);
        }

        // Trả về các token trong phản hồi.
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Đăng xuất người dùng bằng cách vô hiệu hóa JWT hiện tại.
     *
     * @throws ParseException Nếu xảy ra lỗi trong quá trình xử lý token.
     */
    @Override
    public void logout() throws ParseException {
        // Lấy token hiện tại từ header.
        String token = tokenService.getJWT();

        // Lấy giá trị JTI từ token và lưu nó vào Redis để đánh dấu là đã vô hiệu hóa.
        String jti = tokenService.getClaim(token, JTI);
        redisTemplate.opsForValue().set(jti, true, tokenService.getTimeToLive(token), TimeUnit.MINUTES);
    }

    /**
     * Tạo một access token mới nếu refresh token hợp lệ.
     *
     * @return Access token mới.
     * @throws ParseException Nếu xảy ra lỗi trong quá trình xử lý token.
     */
    @Override
    public String getNewToken() throws ParseException {
        // Lấy token hiện tại từ header.
        String token = tokenService.getJWT();

        // Lấy giá trị JTI từ token và kiểm tra xem token có bị vô hiệu hóa không.
        String jti = tokenService.getClaim(token, JTI);
        if (Boolean.TRUE.equals(redisTemplate.hasKey(jti))) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }

        // Đăng xuất token hiện tại để đảm bảo nó không còn hợp lệ.
        logout();

        // Tạo access token mới dựa trên thông tin người dùng.
        String sub = tokenService.getClaim(token, SUB);
        UserAccount userAccounts = userAccountService.getUserByUsername(sub);
        return tokenService.generateToken(userAccounts, 1);
    }
}
