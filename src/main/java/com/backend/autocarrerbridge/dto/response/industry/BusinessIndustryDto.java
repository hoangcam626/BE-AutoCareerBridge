package com.backend.autocarrerbridge.dto.response.industry;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.entity.BusinessIndustry;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessIndustryDto {
    Integer id;
    Integer businessId;
    Integer industryId;
    String industryName;
    String industryCode;
    String businessName;
    Status status;
    LocalDateTime createAt;
    String createBy;

    public BusinessIndustryDto(BusinessIndustry businessIndustry) {
        this.id = businessIndustry.getId();
        this.businessId = businessIndustry.getBusiness().getId();
        this.industryId = businessIndustry.getIndustry().getId();
        this.industryName = businessIndustry.getIndustry().getName();
        this.industryCode = businessIndustry.getIndustry().getCode();
        this.businessName = businessIndustry.getBusiness().getName();
        this.status = businessIndustry.getStatus();
        this.createAt = LocalDateTime.now();
        this.createBy = businessIndustry.getCreatedBy();
    }
}
