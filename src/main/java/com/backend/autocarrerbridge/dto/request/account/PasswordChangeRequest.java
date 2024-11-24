package com.backend.autocarrerbridge.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NOT_EMPTY_PW;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordChangeRequest {
    @NotBlank(message = EMAIL_REQUIRED_MESSAGE)
    String username;
    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String password;
    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String newPassword;
    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String reNewPassword;
}
