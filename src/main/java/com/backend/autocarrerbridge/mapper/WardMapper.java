package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.entity.Ward;

@Mapper(componentModel = "spring")
public interface WardMapper {
    WardResponse toWardResponse(Ward ward);
}
