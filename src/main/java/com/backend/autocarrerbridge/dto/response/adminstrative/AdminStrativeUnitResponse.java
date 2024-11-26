package com.backend.autocarrerbridge.dto.response.adminstrative;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminStrativeUnitResponse {
    private Integer id;

    private String fullName;

    private String fullNameEn;

    private String shortName;

    private String shortNameEn;

    private String codeName;

    private String codeNameEn;
}
