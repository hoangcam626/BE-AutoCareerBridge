package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.entity.Business;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    BusinessResponse toBusinessResponse(Business business);

    void udpateBusiness(@MappingTarget Business business, BusinessUpdateRequest request);
}
