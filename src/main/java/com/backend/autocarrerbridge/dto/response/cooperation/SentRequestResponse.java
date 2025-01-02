package com.backend.autocarrerbridge.dto.response.cooperation;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.entity.BusinessUniversity;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SentRequestResponse {
    Integer id;
    Integer businessId;
    String businessName;
    Integer universityId;
    String universityName;
    Integer businessImageId;
    Integer universityImageId;
    State statusConnected;
    Status status;
    LocalDateTime createAt;
    String createBy;
    LocalDateTime updateAt;
    String updateBy;

    public SentRequestResponse(BusinessUniversity businessUniversity) {
        this.id = businessUniversity.getId();
        this.businessId = businessUniversity.getBusiness().getId();
        this.universityId = businessUniversity.getUniversity().getId();
        this.businessName = businessUniversity.getBusiness().getName();
        this.universityName = businessUniversity.getUniversity().getName();
        this.businessImageId = businessUniversity.getBusiness().getBusinessImageId();
        this.universityImageId = businessUniversity.getUniversity().getLogoImageId();
        this.statusConnected = businessUniversity.getStatusConnected();
        this.status = businessUniversity.getStatus();
        this.createAt = businessUniversity.getCreatedAt();
        this.updateAt = businessUniversity.getUpdatedAt();
        this.createBy = businessUniversity.getCreatedBy();
        this.updateBy = businessUniversity.getUpdatedBy();
    }
}
