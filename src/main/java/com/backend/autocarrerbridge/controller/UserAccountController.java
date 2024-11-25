package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.request.account.ForgotPasswordRequest;
import com.backend.autocarrerbridge.dto.request.account.PasswordChangeRequest;
import com.backend.autocarrerbridge.dto.request.account.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.account.UserAccountLoginResponse;
import com.backend.autocarrerbridge.emailconfig.EmailCodeRequest;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.service.UserAuthentication;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.ParseException;


import static com.backend.autocarrerbridge.util.Constant.CHANGE_PW;
import static com.backend.autocarrerbridge.util.Constant.CREATE_NEW_TOKEN;
import static com.backend.autocarrerbridge.util.Constant.LOGIN_SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.LOGOUT_SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SEND_CODE;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountController {
     UserAccountService userAccountService;
     AuthenticationService authenticationService;
     UserAuthentication userAuthentication;
    @PostMapping("/login")
    public ApiResponse<Object> loginJWT(@RequestBody @Valid UserAccountRequest accountDTO)  {
        UserAccountLoginResponse userAccountDTO = userAccountService.authenticateUser(accountDTO);
        AuthenticationResponse authenticationResponse =  userAuthentication.authenticate(accountDTO.getUsername());
        userAccountDTO.setAccessToken(authenticationResponse.getAccessToken());

        return ApiResponse.builder().message(LOGIN_SUCCESS).code(SUCCESS).data(userAccountDTO).build();
    }

    @PostMapping("/logout")
    public ApiResponse<Object> logout() throws ParseException {
        authenticationService.logout();
        return ApiResponse.builder().message(LOGOUT_SUCCESS).data(LOGOUT_SUCCESS).code(SUCCESS).build();
    }
    @PostMapping("/refresh")
    public ApiResponse<Object> refresh() throws ParseException {
        String newToken = authenticationService.getNewToken();
        return ApiResponse.builder().message(CREATE_NEW_TOKEN).code(SUCCESS).data(newToken).build();
    }

    @PutMapping("/change-password")
    public ApiResponse<Object> changePassword(@RequestBody @Valid PasswordChangeRequest accountDTO){
        return ApiResponse.builder().message(CHANGE_PW).code(SUCCESS).data(userAccountService.updatePassword(accountDTO)).build();
    }
    @PostMapping("/verify")
    public ApiResponse<Object> sendVerify(@RequestBody EmailCodeRequest emailCode){
        return ApiResponse.builder().message(SEND_CODE).code(SUCCESS).data(userAccountService.generateVerificationCode(emailCode.getEmail())).build();
    }
    @PostMapping("/forgot-code")
    public ApiResponse<Object> sendForgotCode(@RequestBody EmailCodeRequest emailCode){
        return ApiResponse.builder().message(SEND_CODE).code(SUCCESS).data(userAccountService.generatePasswordResetCode(emailCode.getEmail())).build();
    }
    @PostMapping("/forgot-pass")
    public ApiResponse<Object> handleNewPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        return ApiResponse.builder().message(SEND_CODE).code(SUCCESS).data(userAccountService.handleForgotPassword(forgotPasswordRequest)).build();
    }
}
