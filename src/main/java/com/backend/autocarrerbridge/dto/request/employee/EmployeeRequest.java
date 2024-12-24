package com.backend.autocarrerbridge.dto.request.employee;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_INVALID_SPACE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_REGEX;
import static com.backend.autocarrerbridge.util.Constant.ERROR_INVALID_PHONE_NUMBER;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NAME_EMPTY;
import static com.backend.autocarrerbridge.util.Constant.GENDER_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_NAME_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_REGEX;
import static com.backend.autocarrerbridge.util.Constant.PHONE_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PHONE_REGEX;
import static com.backend.autocarrerbridge.util.Constant.TOO_LONG_ADDRESS_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TOO_LONG_EMAIL_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TOO_LONG_NAME_MESSAGE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {
    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    @Pattern(regexp = NAME_REGEX, message = INVALID_NAME_MESSAGE)
    @Size(min = 2, max = 255, message = TOO_LONG_NAME_MESSAGE)
    String name;

    @NotBlank(message = GENDER_NOT_BLANK_MESSAGE)
    String gender;

    LocalDate dateOfBirth;

    @NotBlank(message = ERROR_NAME_EMPTY)
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_INVALID_SPACE_MESSAGE)
    @Size(min = 2, max = 255, message = TOO_LONG_EMAIL_MESSAGE)
    String email;

    @Size(max = 255, message = TOO_LONG_ADDRESS_MESSAGE)
    String address;

    MultipartFile employeeImage;

    @NotBlank(message = PHONE_NOT_BLANK_MESSAGE)
    @Pattern(regexp = PHONE_REGEX, message = ERROR_INVALID_PHONE_NUMBER)
    String phone;
}
