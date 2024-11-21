package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.AccountRespone.*;
import com.backend.autocarrerbridge.emailconfig.EmailCode;
import com.backend.autocarrerbridge.entity.UserAccount;

public interface UserAccountService {
    DisplayUserAccountDTO authenticateUser(UserAccountResponseDTO useraccountDTO);
    void saveRefreshTokenForUser(Integer id, String refresh_token);
    UserAccount getUserByUsername(String username);
    UserAccount registerUser(UserAccount userAccount);
    DisplayUserAccountDTO updatePassword(ChangePassWordDTO userAccount);
    EmailCode generateVerificationCode(String email);
    EmailCode generatePasswordResetCode(String email);
    String handleForgotPassword(ForgotPassWordDTO forgotPassWordDTO);

    UserAccount approvedAccount(UserAccount userAccount);
}
