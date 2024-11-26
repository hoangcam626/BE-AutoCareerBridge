package com.backend.autocarrerbridge.dto.request.employee;

import static com.backend.autocarrerbridge.util.Constant.GENDER_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_NOT_BLANK_MESSAGE;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import com.backend.autocarrerbridge.util.enums.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequestUpdate {
    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    String name;

    @NotBlank(message = GENDER_NOT_BLANK_MESSAGE)
    String gender;

    LocalDate dateOfBirth;

    @NotBlank(message = "EMAIL_NOT_BLANK")
    String email;

    String address;

    @NotBlank(message = "CODE_EMPLOYEE_NOT_BLANK")
    String employeeCode;

    Integer employeeImageId;

    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;

    Status status;
}
