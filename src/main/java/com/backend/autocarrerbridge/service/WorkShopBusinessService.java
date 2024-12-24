package com.backend.autocarrerbridge.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopBusinessRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopBusinessReponse;
import com.backend.autocarrerbridge.util.enums.State;

public interface WorkShopBusinessService {
    WorkShopBusinessReponse getAllColabBusiness(Integer workshopId, Pageable pageable, State state);

    String requestToAttend(WorkShopBusinessRequest workShopBusinessRequest);

    String acceptBusiness(WorkShopBusinessRequest workShopBusinessRequest);

    String rejectBusiness(WorkShopBusinessRequest workShopBusinessRequest);

    List<Map<String,Object>> countWorkShopAndStatusConnected();
}
