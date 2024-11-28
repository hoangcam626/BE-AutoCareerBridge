package com.backend.autocarrerbridge.dto.response.adminstrative;

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
public class AdminstrativeRegionResponse {
    private Integer id;

    private String name;

    private String nameEn;

    private String codeName;

    private String codeNameEn;
}
