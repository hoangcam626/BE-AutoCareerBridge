package com.backend.autocarrerbridge.dto.request.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import static com.backend.autocarrerbridge.util.Constant.EMAIL_INVALID_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_INVALID_PHONE_NUMBER;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NAME_EMPTY;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NOT_EMPTY_PW;
import static com.backend.autocarrerbridge.util.Constant.FORGOT_CODE_EMPTY_MESSAGE;


@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUniversityRequest {
    @NotBlank(message = ERROR_NAME_EMPTY)
    String name;
    @NotBlank(message = EMAIL_INVALID_MESSAGE)
    String email;
    @NotBlank(message = ERROR_INVALID_PHONE_NUMBER)
    String phone;
    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String password;
    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String rePassword;
    @NotBlank(message = FORGOT_CODE_EMPTY_MESSAGE)
    String verificationCode;
}
