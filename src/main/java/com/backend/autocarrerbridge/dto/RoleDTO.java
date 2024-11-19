package com.backend.autocarrerbridge.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoleDTO {
    private Integer id;
    private String name;
    private String description;
}
