package com.backend.autocarrerbridge.dto.response.business;

import java.time.LocalDate;

import com.backend.autocarrerbridge.dto.response.LocationResponse;
import com.backend.autocarrerbridge.dto.response.account.UserAccountResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessResponse {
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
    LocalDate createdAt;
    LocalDate updatedAt;
    LocationResponse locationResponse;
    UserAccountResponse userAccountResponse;
}
