package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.entity.Province;

public interface ProvinceService {
    List<ProvinceResponse> getAll();

    ProvinceResponse getById(Integer id);

    Province findProvinceById(Integer id);
}
