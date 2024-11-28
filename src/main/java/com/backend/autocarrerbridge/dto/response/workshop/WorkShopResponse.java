package com.backend.autocarrerbridge.dto.response.workshop;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class WorkShopResponse {
    private Integer id;
    private String title;
    private String description;
    private Status status;
    private State statusBrowse;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDate expireDate;

    private Integer workshopImageId;
    private LocationResponse location;
    private UniversityResponse university;
}
