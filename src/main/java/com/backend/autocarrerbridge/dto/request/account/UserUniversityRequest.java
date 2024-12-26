package com.backend.autocarrerbridge.dto.request.account;

import static com.backend.autocarrerbridge.util.Constant.ADDRESS_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_INVALID_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMPTY_FILE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_INVALID_PHONE_NUMBER;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NAME_EMPTY;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NOT_EMPTY_PW;
import static com.backend.autocarrerbridge.util.Constant.FORGOT_CODE_EMPTY_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_REGISTER;
import static com.backend.autocarrerbridge.util.Constant.PW_REGISTER;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUniversityRequest {

    @Size(max = 128,message = NAME_REGISTER)
    @NotBlank(message = ERROR_NAME_EMPTY)
    String name;

    @Size(max = 320, message = EMAIL_REQUIRED_MESSAGE)
    @NotBlank(message = EMAIL_INVALID_MESSAGE)
    String email;

    @Size(min = 10, max = 10, message = ERROR_INVALID_PHONE_NUMBER)
    @NotBlank(message = ERROR_INVALID_PHONE_NUMBER)
    String phone;

    @NotNull(message = ADDRESS_NOT_FOUND)
    private Integer provinceId;
    @NotNull(message = ADDRESS_NOT_FOUND)
    private Integer districtId;
    @NotNull(message = ADDRESS_NOT_FOUND)
    private Integer wardId;
    @NotNull(message = EMPTY_FILE_MESSAGE)
    @Size(max = 32,message = PW_REGISTER)
    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String password;

    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String rePassword;

    @NotBlank(message = FORGOT_CODE_EMPTY_MESSAGE)
    String verificationCode;
}
