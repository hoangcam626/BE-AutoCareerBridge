package com.backend.autocarrerbridge.dto.response.district;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DistrictResponse {
    private Integer id;

    private String fullName;

    private String fullNameEn;

    private String name;

    private String nameEn;

    private String codeName;
}
