package com.backend.autocarrerbridge.dto.response.province;

import com.backend.autocarrerbridge.dto.response.adminstrative.AdminStrativeUnitResponse;
import com.backend.autocarrerbridge.dto.response.adminstrative.AdminstrativeRegionResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProvinceResponse {
    private Integer id;

    private String fullName;
    @JsonIgnore
    private String fullNameEn;

    private String name;
    @JsonIgnore
    private String nameEn;
    @JsonIgnore
    private String codeName;
    @JsonIgnore
    private AdminStrativeUnitResponse administrativeUnit;
    @JsonIgnore
    private AdminstrativeRegionResponse administrativeRegion;
}
