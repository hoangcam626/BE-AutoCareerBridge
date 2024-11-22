package com.backend.autocarrerbridge.dto.response;

import java.time.LocalDate;

import com.backend.autocarrerbridge.entity.AbstractAudit;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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
