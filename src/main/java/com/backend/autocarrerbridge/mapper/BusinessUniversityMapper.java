package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.response.cooperation.CooperationUniversityResponse;
import com.backend.autocarrerbridge.entity.BusinessUniversity;

@Mapper(componentModel = "spring")
public interface BusinessUniversityMapper {

    CooperationUniversityResponse toCooperationUniversityResponse(BusinessUniversity businessUniversity);
}
