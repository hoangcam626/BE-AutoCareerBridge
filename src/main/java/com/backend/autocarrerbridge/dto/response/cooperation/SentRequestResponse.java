package com.backend.autocarrerbridge.dto.response.cooperation;

import com.backend.autocarrerbridge.entity.BusinessUniversity;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SentRequestResponse {
    Integer id;
    String businessName;
    String universityName;
    State statusConnected;
    Status status;
    LocalDateTime createAt;
    String createBy;
    LocalDateTime updateAt;
    String updateBy;

    public SentRequestResponse(BusinessUniversity businessUniversity) {
        this.id = businessUniversity.getId();
        this.businessName = businessUniversity.getBusiness().getName();
        this.universityName = businessUniversity.getUniversity().getName();
        this.statusConnected = businessUniversity.getStatusConnected();
        this.status = businessUniversity.getStatus();
        this.createAt = businessUniversity.getCreatedAt();
        this.updateAt = businessUniversity.getUpdatedAt();
        this.createBy = businessUniversity.getCreatedBy();
        this.updateBy = businessUniversity.getUpdatedBy();
    }
}
