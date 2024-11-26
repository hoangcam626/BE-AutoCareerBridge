package com.backend.autocarrerbridge.dto.request.account;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.FORGOT_CODE_REQUIRED_MESSAGE;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotPasswordRequest {
    @NotBlank(message = EMAIL_REQUIRED_MESSAGE)
    private String email;

    @NotBlank(message = FORGOT_CODE_REQUIRED_MESSAGE)
    private String forgotCode;
}
