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
    @NotBlank(message = Constant.NAME_NOT_BLANK_MESSAGE)
    String name;
    @NotBlank(message = Constant.GENDER_NOT_BLANK_MESSAGE)
    String gender;

    LocalDate dateOfBirth;
    @NotBlank(message = Constant.EMAIL_NOT_BLANK_MESSAGE)
    String email;
    String address;
    @NotBlank(message = Constant.COED_EMPLOYEE_NOT_BLANK_MESSAGE)
    String employeeCode;
    Integer employeeImageId;
    @NotBlank(message = Constant.PHONE_NOT_BLANK_MESSAGE)

    String phone;
}
