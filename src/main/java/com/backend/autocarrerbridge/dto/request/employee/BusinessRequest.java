package com.backend.autocarrerbridge.dto.request.employee;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessRequest {
    Integer id;
    String name;
    String taxCode;
    String companySize;
    String website;
    Integer foundYear;
    String email;
    String phone;
    String description;
    Integer businessImageId;
    Integer licenseImageId;
    Integer status;
    LocalDate createdAt;
    LocalDate updatedAt;
    LocationRequest locationRequest;
    UserAccountRequest userAccountRequest;
}
