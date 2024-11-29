package com.backend.autocarrerbridge.service;

import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopBusinessRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopBusinessReponse;
import com.backend.autocarrerbridge.util.enums.State;

public interface WorkShopBusinessService {
    WorkShopBusinessReponse getAllColabBusiness(Integer workshopId, Pageable pageable, State state);

    String requestToAttend(WorkShopBusinessRequest workShopBusinessRequest);
    String acceptBusiness(WorkShopBusinessRequest workShopBusinessRequest);
}
