package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.ward.WardResponse;
import com.backend.autocarrerbridge.entity.Ward;

import java.util.List;

public interface WardService {
    List<WardResponse> getAllByDistrictId(Integer districtId);
    WardResponse getById(Integer id);

    Ward findWardById(Integer id);
}
