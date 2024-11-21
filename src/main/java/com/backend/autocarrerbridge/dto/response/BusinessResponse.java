package com.backend.autocarrerbridge.dto.response;

import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.entity.UserAccount;
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
     Integer status;
     LocalDate createdAt;
     LocalDate updatedAt;
     Location location;
     UserAccount userAccount;
}
