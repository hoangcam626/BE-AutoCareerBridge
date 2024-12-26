package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.workshop.StateWorkShopBusinessResponse;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopBusinessRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopBusinessResponse;
import com.backend.autocarrerbridge.util.enums.State;

import java.text.ParseException;

public interface WorkShopBusinessService {
    WorkshopBusinessResponse getAllColabBusiness(Integer workshopId, Pageable pageable, State state);

    String requestToAttend(WorkShopBusinessRequest workShopBusinessRequest);

    String acceptBusiness(WorkShopBusinessRequest workShopBusinessRequest) throws ParseException;

    String rejectBusiness(WorkShopBusinessRequest workShopBusinessRequest) throws ParseException;

    List<Map<String,Object>> countWorkShopAndStatusConnected();

    StateWorkShopBusinessResponse getWorkShopStatusBusiness(Integer workshopId, Integer businessId);
}
