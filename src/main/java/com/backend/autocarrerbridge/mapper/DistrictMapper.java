package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.entity.District;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    DistrictResponse districtToDistrictResponse(District district);
}
