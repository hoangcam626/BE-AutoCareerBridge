package com.backend.autocarrerbridge.dto.request.employee;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {
    @NotBlank(message = "NAME_NOT_BLANK")
    String name;

    @NotBlank(message = "GENDER_NOT_BLANK")
    String gender;

    LocalDate dateOfBirth;

    @NotBlank(message = "EMAIL_NOT_BLANK")
    String email;

    String address;

    String employeeCode;

    MultipartFile employeeImage;

    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;
}
