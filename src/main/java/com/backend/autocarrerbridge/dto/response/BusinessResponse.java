package com.backend.autocarrerbridge.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
