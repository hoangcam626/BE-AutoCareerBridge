package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.account.ForgotPasswordRequest;
import com.backend.autocarrerbridge.dto.request.account.PasswordChangeRequest;
import com.backend.autocarrerbridge.dto.request.account.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.account.UserAccountLoginResponse;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.util.email.EmailCode;

public interface UserAccountService {
    UserAccountLoginResponse authenticateUser(UserAccountRequest userAccountRequest);

    void saveRefreshTokenForUser(Integer id, String refreshToken);

    UserAccount getUserByUsername(String username);

    UserAccount registerUser(UserAccount userAccount);

    UserAccountLoginResponse updatePassword(PasswordChangeRequest userAccount);

    EmailCode generateVerificationCode(String email);

    EmailCode generatePasswordResetCode(String email);

    String handleForgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    void approvedAccount(UserAccount userAccount);

    void rejectedAccount(UserAccount userAccount);


}
