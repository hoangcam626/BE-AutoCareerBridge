package com.backend.autocarrerbridge.dto.response.business;

import lombok.Data;

@Data
public class BusinessSearchPage {
    private Integer id;
    private String name;
    private String description;
    private String province;
    private String district;
    private String ward;
    private String locationDescription;
    private Integer imageID;
    private Long totalJobRecruit;

    public BusinessSearchPage(Integer id, String name, String description, String province, String district, String ward, String locationDescription, Integer imageID, Long totalJobRecruit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.locationDescription = locationDescription;
        this.imageID = imageID;
        this.totalJobRecruit = totalJobRecruit;
    }
}
