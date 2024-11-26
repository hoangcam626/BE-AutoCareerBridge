package com.backend.autocarrerbridge.dto.request.account;

import static com.backend.autocarrerbridge.util.Constant.ERROR_TOKEN_INVALID_MESSAGE;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequest {
    @NotBlank(message = ERROR_TOKEN_INVALID_MESSAGE)
    private String accessToken;
}
