package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.entity.Ward;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WardMapper {
    WardResponse toWardResponse(Ward ward);
}
