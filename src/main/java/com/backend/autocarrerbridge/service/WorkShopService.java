package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopPortalResponse;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopApprovedRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopRejectedRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkShopUniversityResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopApprovedResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopRejectedResponse;
import com.backend.autocarrerbridge.util.enums.State;

public interface WorkShopService {
    List<WorkShopResponse> getAllWorkShop(Pageable pageable, String keyword);

    WorkShopUniversityResponse getAllWorkShopByUniversity(Pageable pageable, Integer universityId, String keyword);

    WorkShopResponse createWorkShop(WorkShopRequest workShopRequest);

    List<WorkShopResponse> getAllWorkShopByState(Pageable pageable, State state, String keyword);

    List<WorkShopResponse> getAllWorkShopByLocation(Pageable pageable, Integer provinceId);

    WorkShopResponse updateWordShop(Integer id, WorkShopRequest workShopRequest);

    WorkShopResponse removeWorkShop(Integer id);

    WorkShopResponse getWorkShopById(Integer id);

    WorkshopApprovedResponse approved(WorkshopApprovedRequest req) throws ParseException;

    WorkshopRejectedResponse rejected(WorkshopRejectedRequest req) throws ParseException;

    PagingResponse<WorkShopResponse> getPagingByState(PageInfo info, State state);
    long countWorkShop();



    PagingResponse<WorkShopPortalResponse> getAllWorkShopApprovedAndLocation(Pageable pageable, LocalDate startDate,LocalDate endDate, Integer provinceId,Integer universityId, String keyword);

    WorkShopPortalResponse getWorkShopPortalById(Integer workShopId);

}
