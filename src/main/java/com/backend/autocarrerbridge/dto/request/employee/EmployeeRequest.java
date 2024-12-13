package com.backend.autocarrerbridge.dto.request.employee;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.backend.autocarrerbridge.exception.ErrorCode.GENDER_NOT_BLANK;
import static com.backend.autocarrerbridge.exception.ErrorCode.NAME_NOT_BLANK;
import static com.backend.autocarrerbridge.exception.ErrorCode.PHONE_NOT_BLANK;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NAME_EMPTY;
import static com.backend.autocarrerbridge.util.Constant.GENDER_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PHONE_NOT_BLANK_MESSAGE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {
    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    String name;

    @NotBlank(message = GENDER_NOT_BLANK_MESSAGE)
    String gender;

    LocalDate dateOfBirth;

    @NotBlank(message = ERROR_NAME_EMPTY)
    String email;

    String address;

    MultipartFile employeeImage;

    @NotBlank(message = PHONE_NOT_BLANK_MESSAGE)
    String phone;
}
