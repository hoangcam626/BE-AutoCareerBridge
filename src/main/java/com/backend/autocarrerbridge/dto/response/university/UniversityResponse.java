package com.backend.autocarrerbridge.dto.response.university;

import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UniversityResponse {
    private Integer id;
    private Status status;
    private String description;
    private String email;
    private Integer foundedYear;
    private Integer logoImageId;
    private String name;
    private String phone;
    private String website;
    private Integer locationId;
}
