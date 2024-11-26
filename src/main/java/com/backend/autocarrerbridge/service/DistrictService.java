package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.district.DistrictResponse;
import com.backend.autocarrerbridge.entity.District;

import java.util.List;

public interface DistrictService {
    List<DistrictResponse> getAllByProvinceId(Integer provinceId);
    DistrictResponse getById(Integer id);

    District findDistrictById(Integer id);
}
