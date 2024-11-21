package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.response.BusinessResponse;
import com.backend.autocarrerbridge.entity.Business;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessMapper {
    BusinessResponse toBusinessResponse(Business business);

}
