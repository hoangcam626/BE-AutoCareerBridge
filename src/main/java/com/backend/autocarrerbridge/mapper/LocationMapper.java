package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationResponse toLocationResponse(Location location);

}
