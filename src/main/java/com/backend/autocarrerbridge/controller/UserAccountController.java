package com.backend.autocarrerbridge.controller;

import static com.backend.autocarrerbridge.util.Constant.CHANGE_PW;
import static com.backend.autocarrerbridge.util.Constant.CREATE_NEW_TOKEN;
import static com.backend.autocarrerbridge.util.Constant.LOGIN_SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.LOGOUT_SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SEND_CODE;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;

import java.text.ParseException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.account.ForgotPasswordRequest;
import com.backend.autocarrerbridge.dto.request.account.PasswordChangeRequest;
import com.backend.autocarrerbridge.dto.request.account.TokenRefreshRequest;
import com.backend.autocarrerbridge.dto.request.account.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;
import com.backend.autocarrerbridge.dto.response.account.UserAccountLoginResponse;
import com.backend.autocarrerbridge.util.email.EmailCodeRequest;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.service.UserAuthentication;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Controller quản lý các API liên quan đến tài khoản người dùng.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountController {
    UserAccountService userAccountService;
    AuthenticationService authenticationService;
    UserAuthentication userAuthentication;

    /**
     * Xử lý đăng nhập bằng JWT.
     * @param accountDTO Thông tin tài khoản người dùng.
     * @return Thông tin đăng nhập cùng với access token.
     */
    @PostMapping("/login")
    public ApiResponse<Object> loginJWT(@RequestBody @Valid UserAccountRequest accountDTO) {
        UserAccountLoginResponse userAccountDTO = userAccountService.authenticateUser(accountDTO);
        AuthenticationResponse authenticationResponse = userAuthentication.authenticate(accountDTO.getUsername());
        userAccountDTO.setAccessToken(authenticationResponse.getAccessToken());
        return ApiResponse.builder()
                .message(LOGIN_SUCCESS)
                .code(SUCCESS)
                .data(userAccountDTO)
                .build();
    }

    /**
     * Xử lý đăng xuất.
     * @return Thông báo đăng xuất thành công.
     */
    @PostMapping("/logout")
    public ApiResponse<Object> logout() throws ParseException {
        authenticationService.logout();
        return ApiResponse.builder()
                .message(LOGOUT_SUCCESS)
                .data(LOGOUT_SUCCESS)
                .code(SUCCESS)
                .build();
    }

    /**
     * Tạo mới token để duy trì phiên đăng nhập.
     * @return Token mới được tạo.
     */
    @PostMapping("/refresh")
    public ApiResponse<Object> refresh() throws ParseException {
        String newToken = authenticationService.getNewToken();
        return ApiResponse.builder()
                .message(CREATE_NEW_TOKEN)
                .code(SUCCESS)
                .data(new TokenRefreshRequest(newToken))
                .build();
    }

    /**
     * Thay đổi mật khẩu cho người dùng.
     * @param accountDTO Thông tin yêu cầu thay đổi mật khẩu.
     * @return Kết quả cập nhật mật khẩu.
     */
    @PutMapping("/change-password")
    public ApiResponse<Object> changePassword(@RequestBody @Valid PasswordChangeRequest accountDTO) {
        return ApiResponse.builder()
                .message(CHANGE_PW)
                .code(SUCCESS)
                .data(userAccountService.updatePassword(accountDTO))
                .build();
    }

    /**
     * Gửi mã xác minh đến email người dùng.
     * @param emailCode Email cần gửi mã.
     * @return Kết quả gửi mã xác minh.
     */
    @PostMapping("/verify")
    public ApiResponse<Object> sendVerify(@RequestBody @Valid EmailCodeRequest emailCode) {
        return ApiResponse.builder()
                .message(SEND_CODE)
                .code(SUCCESS)
                .data(userAccountService.generateVerificationCode(emailCode.getEmail()))
                .build();
    }

    /**
     * Gửi mã để đặt lại mật khẩu.
     * @param emailCode Email cần nhận mã.
     * @return Kết quả gửi mã đặt lại mật khẩu.
     */
    @PostMapping("/forgot-code")
    public ApiResponse<Object> sendForgotCode(@RequestBody @Valid EmailCodeRequest emailCode) {
        return ApiResponse.builder()
                .message(SEND_CODE)
                .code(SUCCESS)
                .data(userAccountService.generatePasswordResetCode(emailCode.getEmail()))
                .build();
    }

    /**
     * Xử lý đặt lại mật khẩu mới.
     * @param forgotPasswordRequest Thông tin yêu cầu đặt lại mật khẩu.
     * @return Kết quả đặt lại mật khẩu.
     */
    @PostMapping("/forgot-pass")
    public ApiResponse<Object> handleNewPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        return ApiResponse.builder()
                .message(SEND_CODE)
                .code(SUCCESS)
                .data(userAccountService.handleForgotPassword(forgotPasswordRequest))
                .build();
    }
}
