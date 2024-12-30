package com.backend.autocarrerbridge.dto.response.university;

import lombok.Data;

@Data
public class UniversitySearchPage {
    private Integer id;
    private String name;
    private String description;
    private String province;
    private String district;
    private String ward;
    private String locationDescription;
    private Integer imageID;
    private Long totalCooperationApprove;

    public UniversitySearchPage(Integer id, String name, String description, String province, String district, String ward, String locationDescription, Integer imageID, Long totalCooperationApprove) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.locationDescription = locationDescription;
        this.imageID = imageID;
        this.totalCooperationApprove = totalCooperationApprove;
    }
}
