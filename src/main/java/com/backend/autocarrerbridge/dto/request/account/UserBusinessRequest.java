package com.backend.autocarrerbridge.dto.request.account;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMPTY_FILE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NAME_EMPTY;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NOT_EMPTY_PW;
import static com.backend.autocarrerbridge.util.Constant.FORGOT_CODE_EMPTY_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_REGISTER;
import static com.backend.autocarrerbridge.util.Constant.PW_REGISTER;
import static com.backend.autocarrerbridge.util.Constant.TAX_CODE;
import static com.backend.autocarrerbridge.util.Constant.TAX_CODE_REGISTER;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBusinessRequest {

    @Size(max = 128,message = NAME_REGISTER)
    @NotBlank(message = ERROR_NAME_EMPTY)
    String name;

    @Size(max = 32,message = TAX_CODE_REGISTER)
    @NotBlank(message = TAX_CODE)
    String taxCode;

    @Size(max = 320, message = EMAIL_REQUIRED_MESSAGE)
    @NotBlank(message = EMAIL_REQUIRED_MESSAGE)
    String email;

    @Size(max = 32,message = PW_REGISTER)
    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String password;

    @NotBlank(message = ERROR_NOT_EMPTY_PW)
    String rePassword;

    @NotNull(message = EMPTY_FILE_MESSAGE)
    MultipartFile licenseImage;

    @NotBlank(message = FORGOT_CODE_EMPTY_MESSAGE)
    String verificationCode;
}
