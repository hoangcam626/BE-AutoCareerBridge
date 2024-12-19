package com.backend.autocarrerbridge.dto.response.business;

import lombok.Data;

@Data
public class IntroduceBusiness {
    private Integer id;
    private String businessName;
    private String industryName;
    private Long totalJob;
    private Integer imageID;

    public IntroduceBusiness(Integer id, String businessName, String industryName, Long totalJob, Integer imageID) {
        this.id = id;
        this.businessName = businessName;
        this.industryName = industryName;
        this.totalJob = totalJob;
        this.imageID = imageID;
    }
}
