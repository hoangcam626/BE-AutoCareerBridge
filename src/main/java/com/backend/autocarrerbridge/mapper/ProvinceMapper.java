package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.entity.Province;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {
    ProvinceResponse toProvinceResponse(Province province);
}
