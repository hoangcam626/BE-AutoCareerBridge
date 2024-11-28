package com.backend.autocarrerbridge.dto.response.employee;

import java.time.LocalDate;

import com.backend.autocarrerbridge.dto.response.abstractaudit.AbstractAuditResponse;
import com.backend.autocarrerbridge.dto.response.account.UserAccountResponse;
import com.backend.autocarrerbridge.entity.Employee;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse extends AbstractAuditResponse {
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

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.gender = employee.getGender();
        this.dateOfBirth = employee.getDateOfBirth();
        this.email = employee.getEmail();
        this.address = employee.getAddress();
        this.employeeCode = employee.getEmployeeCode();
        this.employeeImageId = employee.getEmployeeImageId();
        this.phone = employee.getPhone();
        this.status = employee.getStatus();
    }
}
