package com.backend.autocarrerbridge.dto.response.province;

import com.backend.autocarrerbridge.dto.response.adminstrative.AdminStrativeUnitResponse;
import com.backend.autocarrerbridge.dto.response.adminstrative.AdminstrativeRegionResponse;

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

    private String fullNameEn;

    private String name;

    private String nameEn;

    private String codeName;

    private AdminStrativeUnitResponse administrativeUnit;

    private AdminstrativeRegionResponse administrativeRegion;
}
