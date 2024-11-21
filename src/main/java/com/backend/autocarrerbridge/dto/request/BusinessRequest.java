package com.backend.autocarrerbridge.dto.request;

import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.entity.UserAccount;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
     Location location;
     UserAccount userAccount;
}
