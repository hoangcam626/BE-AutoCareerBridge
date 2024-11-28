package com.backend.autocarrerbridge.dto.response.location;

import com.backend.autocarrerbridge.dto.response.abstractaudit.AbstractAuditResponse;
import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.dto.response.ward.WardResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LocationResponse extends AbstractAuditResponse {

    private Integer id;

    private String description;

    private ProvinceResponse province;

    private DistrictResponse district;

    private WardResponse ward;

}