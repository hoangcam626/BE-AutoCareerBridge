package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.response.cooperation.CooperationUniversityResponse;
import com.backend.autocarrerbridge.entity.BusinessUniversity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessUniversityMapper {

    CooperationUniversityResponse toCooperationUniversityResponse(BusinessUniversity businessUniversity);

}
