package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopPortalResponse;
import com.backend.autocarrerbridge.dto.response.workshop.AdminWorkshopResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopStateBusiness;
import com.backend.autocarrerbridge.entity.Workshop;
import org.springframework.data.domain.Pageable;

import com.backend.autocarrerbridge.dto.request.workshop.WorkShopRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopApprovedRequest;
import com.backend.autocarrerbridge.dto.request.workshop.WorkshopRejectedRequest;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopUniversityResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopApprovedResponse;
import com.backend.autocarrerbridge.dto.response.workshop.WorkshopRejectedResponse;
import com.backend.autocarrerbridge.util.enums.State;

public interface WorkShopService {
    List<WorkshopResponse> getAllWorkShop(Pageable pageable, String keyword);

    WorkshopUniversityResponse getAllWorkShopByUniversity(Pageable pageable, Integer universityId, String keyword);

    WorkshopResponse createWorkShop(WorkShopRequest workShopRequest);

    List<WorkshopResponse> getAllWorkShopByState(Pageable pageable, State state, String keyword);

    List<WorkshopResponse> getAllWorkShopByLocation(Pageable pageable, Integer provinceId);

    WorkshopResponse updateWordShop(Integer id, WorkShopRequest workShopRequest);

    WorkshopResponse removeWorkShop(Integer id,String content) throws ParseException;

    WorkshopResponse getWorkShopById(Integer id);

    WorkshopApprovedResponse approved(WorkshopApprovedRequest req) throws ParseException;

    WorkshopRejectedResponse rejected(WorkshopRejectedRequest req) throws ParseException;

    PagingResponse<AdminWorkshopResponse> getPagingByState(PageInfo info, State state);

    long countWorkShop();

    PagingResponse<WorkshopPortalResponse> getAllWorkShopApprovedAndLocation(Pageable pageable, LocalDate startDate, LocalDate endDate, Integer provinceId, Integer universityId, String keyword);

    WorkshopPortalResponse getWorkShopPortalById(Integer workShopId);

    PagingResponse<AdminWorkshopResponse> getPagingWorkshop(PageInfo req);

    AdminWorkshopResponse detail(Integer id);

    List<Workshop> findAll();

    PagingResponse<WorkshopStateBusiness> getAllWorkShopByPracticeBusiness(Pageable pageable,Integer businessID, String keyword,State state);

}
