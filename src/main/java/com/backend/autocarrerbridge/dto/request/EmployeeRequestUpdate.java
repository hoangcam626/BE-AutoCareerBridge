package com.backend.autocarrerbridge.dto.request;

import com.backend.autocarrerbridge.util.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequestUpdate {
    String name;

    String gender;

    LocalDate dateOfBirth;

    String email;

    String address;

    String employeeCode;

    Integer employeeImageId;

    String phone;

    Status status;
}
