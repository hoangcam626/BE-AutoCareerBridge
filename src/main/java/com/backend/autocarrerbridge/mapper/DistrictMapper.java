package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.entity.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    DistrictResponse districtToDistrictResponse(District district);
}
