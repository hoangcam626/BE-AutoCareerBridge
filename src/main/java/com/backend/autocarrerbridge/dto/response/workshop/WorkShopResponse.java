package com.backend.autocarrerbridge.dto.response.workshop;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate expireDate;

    private Integer workshopImageId;
    private LocationResponse location;
}
