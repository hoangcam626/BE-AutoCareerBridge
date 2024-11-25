package com.backend.autocarrerbridge.dto.request.employee;

import java.time.LocalDate;

import com.backend.autocarrerbridge.util.Constant;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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
    @NotBlank(message = "CODE_EMPLOYEE_NOT_BLANK")
    String employeeCode;

    Integer employeeImageId;

    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;
}
