package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.entity.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationResponse toLocationResponse(Location location);
}
