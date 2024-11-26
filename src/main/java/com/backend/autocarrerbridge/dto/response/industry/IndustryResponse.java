package com.backend.autocarrerbridge.dto.response.industry;

import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryResponse {
    private Integer id;
    private String name;
    private String code;
    private Status status;
    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime updateAt;
    private String updateBy;

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
