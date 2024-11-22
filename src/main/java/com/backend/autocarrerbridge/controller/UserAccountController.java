package com.backend.autocarrerbridge.controller;

import java.text.ParseException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.backend.autocarrerbridge.dto.AccountRespone.*;
import com.backend.autocarrerbridge.emailconfig.EmailCodeRequest;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.service.UserAuthentication;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountController {
    UserAccountService userAccountService;
    AuthenticationService authenticationService;
    UserAuthentication userAuthentication;

    @PostMapping("/login")
    public ApiResponse<?> loginJWT(@RequestBody @Valid UserAccountResponseDTO accountDTO) {
        DisplayUserAccountDTO userAccountDTO = userAccountService.authenticateUser(accountDTO);
        AuthenticationResponse authenticationResponse = userAuthentication.authenticate(accountDTO.getUsername());
        userAccountDTO.setAccessToken(authenticationResponse.getAccessToken());

        return ApiResponse.builder()
                .message("Login success")
                .code(200)
                .data(userAccountDTO)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestHeader("Authorization") String authorizationHeader) throws ParseException {
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }
        authenticationService.logout(token);
        return ApiResponse.builder()
                .message("Logout success")
                .data("Logout Success")
                .code(200)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<?> refresh(@RequestBody TokenRefreshRequest token) throws ParseException {
        String newToken = authenticationService.getNewToken(token.getRefreshToken());
        return ApiResponse.builder()
                .message("Refresh Token Success")
                .code(200)
                .data(newToken)
                .build();
    }

    @PutMapping("/change-password")
    public ApiResponse<?> changePassword(@RequestBody @Valid ChangePassWordDTO accountDTO) {
        return ApiResponse.builder()
                .message("Change Password Success")
                .code(200)
                .data(userAccountService.updatePassword(accountDTO))
                .build();
    }

    @PostMapping("/verify")
    public ApiResponse<?> sendVerify(@RequestBody EmailCodeRequest emailCode) {
        return ApiResponse.builder()
                .message("Send email code success")
                .code(200)
                .data(userAccountService.generateVerificationCode(emailCode.getEmail()))
                .build();
    }

    @PostMapping("/forgot-code")
    public ApiResponse<?> sendForgotCode(@RequestBody EmailCodeRequest emailCode) {
        return ApiResponse.builder()
                .message("Send email code success")
                .code(200)
                .data(userAccountService.generatePasswordResetCode(emailCode.getEmail()))
                .build();
    }

    @PostMapping("/forgot-pass")
    public ApiResponse<?> handleNewPassword(@RequestBody ForgotPassWordDTO forgotPassWordDTO) {
        return ApiResponse.builder()
                .message("Created new password")
                .code(200)
                .data(userAccountService.handleForgotPassword(forgotPassWordDTO))
                .build();
    }
}
