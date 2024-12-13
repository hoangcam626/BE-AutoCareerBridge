package com.backend.autocarrerbridge.dto.response.business;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.dto.response.account.UserAccountResponse;
import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.entity.Business;

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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocationResponse location;
    UserAccountResponse userAccount;

    public BusinessResponse(Business business) {
        this.id = business.getId();
        this.name = business.getName();
        this.taxCode = business.getTaxCode();
        this.companySize = business.getCompanySize();
        this.website = business.getWebsite();
        this.foundYear = business.getFoundYear();
        this.email = business.getEmail();
        this.phone = business.getPhone();
        this.description = business.getDescription();
        this.businessImageId = business.getBusinessImageId();
        this.licenseImageId = business.getLicenseImageId();
    }
}
