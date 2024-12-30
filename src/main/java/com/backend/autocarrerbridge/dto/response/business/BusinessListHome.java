package com.backend.autocarrerbridge.dto.response.business;

import lombok.Data;

@Data
public class BusinessListHome {
    private Integer id;
    private String businessName;
    private String description;
    private Long totalJob;
    private Integer imageID;
    private Integer licenseImageId;

    public BusinessListHome(Integer id, String businessName, String description, Long totalJob, Integer imageID, Integer licenseImageId) {
        this.id = id;
        this.businessName = businessName;
        this.description = description;
        this.totalJob = totalJob;
        this.imageID = imageID;
        this.licenseImageId = licenseImageId;
    }
}
