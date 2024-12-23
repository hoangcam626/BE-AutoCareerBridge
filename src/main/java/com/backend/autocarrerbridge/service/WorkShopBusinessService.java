package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.workshop.StateWorkShopBusinessResponse;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopBusinessRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopBusinessResponse;
import com.backend.autocarrerbridge.util.enums.State;

public interface WorkShopBusinessService {
    WorkShopBusinessResponse getAllColabBusiness(Integer workshopId, Pageable pageable, State state);

    String requestToAttend(WorkShopBusinessRequest workShopBusinessRequest);

    String acceptBusiness(WorkShopBusinessRequest workShopBusinessRequest);

    String rejectBusiness(WorkShopBusinessRequest workShopBusinessRequest);

    StateWorkShopBusinessResponse getWorkShopStatusBusiness(Integer workshopId, Integer businessId);
}
