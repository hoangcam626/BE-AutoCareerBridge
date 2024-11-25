package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopResponse;

import com.backend.autocarrerbridge.util.enums.State;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkShopService {
    List<WorkShopResponse> getAllWorkShop(Pageable pageable);
    List<WorkShopResponse> getAllWorkShopByUniversity(Pageable pageable,Integer universityId);
    List<WorkShopResponse> getAllWorkShopByLocation();
    WorkShopResponse createWorkShop(WorkShopRequest workShopRequest);
    List<WorkShopResponse> getAllWorkShopByState(Pageable pageable, State state);
    WorkShopResponse updateWordShop(Integer id,WorkShopRequest workShopRequest);
    WorkShopResponse removeWorkShop(Integer id);
    WorkShopResponse getWorkShopById(Integer id);
}
