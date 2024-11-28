package com.backend.autocarrerbridge.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.entity.Business;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface BusinessMapper {

    @Mapping(source = "location", target = "location")
    BusinessResponse toBusinessResponse(Business business);

    void updateBusiness(@MappingTarget Business business, BusinessUpdateRequest request);
}
