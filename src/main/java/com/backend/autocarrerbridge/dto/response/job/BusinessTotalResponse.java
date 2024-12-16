package com.backend.autocarrerbridge.dto.response.job;

import lombok.Data;

@Data
public class BusinessTotalResponse {
    private Integer id;
    private String businessName;
    private Long totalJob;

    public BusinessTotalResponse(Integer id, String businessName, Long totalJob) {
        this.id = id;
        this.businessName = businessName;
        this.totalJob = totalJob;
    }
}
