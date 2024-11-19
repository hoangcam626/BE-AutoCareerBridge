package com.backend.autocarrerbridge.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessDTO {
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
}
