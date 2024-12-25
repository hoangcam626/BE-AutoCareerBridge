package com.backend.autocarrerbridge.dto.response.workshop;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminWorkshopResponse {

    private Integer id;

    private String title;

    private String description;

    private Status status;

    private State statusBrowse;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDate expireDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;

    private Integer workshopImageId;

    private LocationResponse location;

    private UniversityResponse university;
}

