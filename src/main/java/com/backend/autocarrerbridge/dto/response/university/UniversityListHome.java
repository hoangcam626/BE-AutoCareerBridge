package com.backend.autocarrerbridge.dto.response.university;

import lombok.Data;

@Data
public class UniversityListHome {
    private Integer id;
    private String universityName;
    private String description;
    private Integer imageID;
    private Long totalCooperation;


    public UniversityListHome(Integer id, String universityName, String description, Integer imageID, Long totalCooperation) {
        this.id = id;
        this.universityName = universityName;
        this.description = description;
        this.imageID = imageID;
        this.totalCooperation = totalCooperation;
    }
}
