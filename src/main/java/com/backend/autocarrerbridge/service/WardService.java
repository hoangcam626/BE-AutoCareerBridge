package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.entity.Ward;

public interface WardService {
    List<WardResponse> getAllByDistrictId(Integer districtId);

    WardResponse getById(Integer id);

    Ward findWardById(Integer id);
}
