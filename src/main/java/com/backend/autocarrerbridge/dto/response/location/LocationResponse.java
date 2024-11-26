package com.backend.autocarrerbridge.dto.response.location;

import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.entity.AbstractAudit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LocationResponse extends AbstractAudit {
    private Integer id;

    private String description;

    private ProvinceResponse provinces;

    private DistrictResponse districts;

    private WardResponse wards;
}
