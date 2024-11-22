package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.response.BusinessResponse;
import com.backend.autocarrerbridge.entity.Business;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    BusinessResponse toBusinessResponse(Business business);
}
