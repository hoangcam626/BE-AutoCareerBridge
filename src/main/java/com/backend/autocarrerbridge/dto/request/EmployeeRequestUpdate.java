package com.backend.autocarrerbridge.dto.request;

import java.time.LocalDate;

 import com.backend.autocarrerbridge.util.Constant;
import com.backend.autocarrerbridge.util.enums.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequestUpdate {
    @NotBlank(message = Constant.NAME_NOT_BLANK_MESSAGE)
    String name;
    @NotBlank(message = "GENDER_NOT_BLANK")
    String gender;
    LocalDate dateOfBirth;
    @NotBlank(message = Constant.EMAIL_NOT_BLANK_MESSAGE)
    String email;

    String address;
    @NotBlank(message = Constant.COED_EMPLOYEE_NOT_BLANK_MESSAGE)
    String employeeCode;

    Integer employeeImageId;

    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;

    Status status;
}

