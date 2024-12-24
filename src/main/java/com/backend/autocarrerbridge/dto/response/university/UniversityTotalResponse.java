package com.backend.autocarrerbridge.dto.response.university;

import lombok.Data;

@Data
public class UniversityTotalResponse {
    private Integer id;
    private String universityName;

    public UniversityTotalResponse(Integer id, String universityName) {
        this.id = id;
        this.universityName = universityName;
    }
}
