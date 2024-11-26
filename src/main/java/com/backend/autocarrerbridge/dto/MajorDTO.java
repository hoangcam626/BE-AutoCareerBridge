package com.backend.autocarrerbridge.dto;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorDTO {

    private Integer id;
    private String code;
    private String name;
    private Status status;
    private Integer numberStudent;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
    private Integer sectionId;
}
