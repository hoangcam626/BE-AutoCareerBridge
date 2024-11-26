package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.province.ProvinceResponse;
import com.backend.autocarrerbridge.entity.Province;

import java.util.List;

public interface ProvinceService {
    List<ProvinceResponse> getAll();
    ProvinceResponse getById(Integer id);

    Province findProvinceById(Integer id);
}
