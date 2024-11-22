package com.backend.autocarrerbridge.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {
    String name;

    String gender;

    LocalDate dateOfBirth;

    String email;

    String address;

    String employeeCode;

    Integer employeeImageId;

    String phone;
}
