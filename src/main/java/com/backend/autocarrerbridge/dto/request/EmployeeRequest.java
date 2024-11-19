package com.backend.autocarrerbridge.dto.request;

import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.UserAccount;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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

    Business business;

    UserAccount userAccount;
}


