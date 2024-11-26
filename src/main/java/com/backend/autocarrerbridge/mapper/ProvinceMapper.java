package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.entity.Province;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {
    ProvinceResponse toProvinceResponse(Province province);

}
