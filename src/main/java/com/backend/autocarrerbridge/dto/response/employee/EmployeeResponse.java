package com.backend.autocarrerbridge.dto.response.employee;

import java.time.LocalDate;

import com.backend.autocarrerbridge.dto.response.account.UserAccountResponse;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    Integer id;

    String name;

    String gender;

    LocalDate dateOfBirth;

    String email;

    String address;

    String employeeCode;

    Integer employeeImageId;

    String phone;

    Integer businessId;

    Status status;

    UserAccountResponse userAccount;
}
