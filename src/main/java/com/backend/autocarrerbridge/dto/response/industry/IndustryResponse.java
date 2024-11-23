package com.backend.autocarrerbridge.dto.response.industry;

import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustryResponse {
    private Integer id;
    private String name;
    private String code;
    private Status status;

    public IndustryResponse(Industry industry) {
        this.id = industry.getId();
        this.name = industry.getName();
        this.code = industry.getCode();
        this.status = industry.getStatus();
    }
}
