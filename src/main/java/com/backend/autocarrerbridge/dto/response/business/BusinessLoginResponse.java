package com.backend.autocarrerbridge.dto.response.business;

import java.time.LocalDate;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessLoginResponse {
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
    LocationResponse location;
}
