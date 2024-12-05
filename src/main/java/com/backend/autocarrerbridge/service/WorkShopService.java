package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopUniversityResponse;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopApprovedRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopRejectedRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopApprovedResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopRejectedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopResponse;
import com.backend.autocarrerbridge.util.enums.State;

public interface WorkShopService {
    List<WorkShopResponse> getAllWorkShop(Pageable pageable, String keyword);

    WorkShopUniversityResponse getAllWorkShopByUniversity(Pageable pageable, Integer universityId, String keyword);

    WorkShopResponse createWorkShop(WorkShopRequest workShopRequest);

    List<WorkShopResponse> getAllWorkShopByState(Pageable pageable, State state, String keyword);

    WorkShopResponse updateWordShop(Integer id, WorkShopRequest workShopRequest);

    WorkShopResponse removeWorkShop(Integer id);

    WorkShopResponse getWorkShopById(Integer id);

    WorkshopApprovedResponse approved(WorkshopApprovedRequest req) throws ParseException;

    WorkshopRejectedResponse rejected(WorkshopRejectedRequest req) throws ParseException;
}
