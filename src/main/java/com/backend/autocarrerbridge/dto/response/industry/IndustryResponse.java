package com.backend.autocarrerbridge.dto.response.industry;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.entity.Industry;
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
public class IndustryResponse {
    Integer id;
    String name;
    String code;
    Status status;
    LocalDateTime createAt;
    String createBy;
    LocalDateTime updateAt;
    String updateBy;

    public IndustryResponse(Industry industry) {
        this.id = industry.getId();
        this.name = industry.getName();
        this.code = industry.getCode();
        this.status = industry.getStatus();
        this.createAt = industry.getCreatedAt();
        this.createBy = industry.getCreatedBy();
        this.updateAt = industry.getUpdatedAt();
        this.updateBy = industry.getUpdatedBy();
    }
}
