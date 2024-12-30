package com.backend.autocarrerbridge.dto.response.business;

import lombok.Data;

@Data
public class IntroduceBusiness {
    private Integer id;
    private String businessName;
    private String description;
    private Integer industryId;
    private String industryName;
    private Long totalJob;
    private Integer imageID;
    private Integer licenseImageId;

    public IntroduceBusiness(Integer id, String businessName, String description, Integer industryId, String industryName, Long totalJob, Integer imageID, Integer licenseImageId) {
        this.id = id;
        this.businessName = businessName;
        this.description = description;
        this.industryName = industryName;
        this.totalJob = totalJob;
        this.industryId = industryId;
        this.imageID = imageID;
        this.licenseImageId = licenseImageId;
    }
}
