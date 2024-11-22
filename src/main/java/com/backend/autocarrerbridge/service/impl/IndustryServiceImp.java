package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;
import static com.backend.autocarrerbridge.util.Constant.DELETED;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.industry.IndustryPagingRequest;
import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;
import com.backend.autocarrerbridge.dto.request.industry.IndustryUpdateRequest;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.repository.IndustryRepo;
import com.backend.autocarrerbridge.service.IndustryService;
import com.backend.autocarrerbridge.util.Constant;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndustryServiceImp implements IndustryService {

    private final IndustryRepo industryRepo;

    @Override
    public ApiResponse<Object> getAllIndustryPaging(int first, int rows, int page, String name, String code) {
        Pageable pageable = PageRequest.of(page, rows);
        Page<IndustryResponse> industryList = industryRepo.getAllIndustryActivePag(name, code, pageable);
        if (industryList.isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        IndustryPagingRequest industryPagingRequest = new IndustryPagingRequest(industryList.getTotalElements(), industryList.getContent());
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industryPagingRequest);
    }

    @Override
    public ApiResponse<Object> getAllIndustry() {
        List<IndustryResponse> list = industryRepo.getAllIndustryActive();
        if (list.isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, list);
    }

    @Override
    public ApiResponse<IndustryResponse> createIndustry(IndustryRequest industryRequest) {
        Industry industry = new Industry();
        if (industryRequest.getName() == null || industryRequest.getName().isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        if (industryRepo.existsByName(industryRequest.getName())) {
            throw new AppException(ERROR_EXIST_NAME);
        }
        if (industryRepo.existsByCode(industryRequest.getCode())) {
            throw new AppException(ERROR_EXIST_CODE);
        }
        if (industryRepo.existsByName(industryRequest.getName()) && industryRepo.existsByCode(industryRequest.getCode())) {
            throw new AppException(ERROR_EXIST_NAME_AND_CODE);
        }
        industry.setName(industryRequest.getName());
        industry.setCode(industryRequest.getCode());
        industry.setStatus(Status.ACTIVE);
        industryRepo.save(industry);
        IndustryResponse industryResponse = new IndustryResponse(industry);
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industryResponse);
    }

    @Override
    public ApiResponse<IndustryResponse> updateIndustry(IndustryUpdateRequest industryUpdateRequest) {
        Industry industry = industryRepo.getIndustriesById(industryUpdateRequest.getId());

        if (industry == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        boolean isNameSame = industry.getName().equals(industryUpdateRequest.getName());
        boolean isCodeSame = industry.getCode().equals(industryUpdateRequest.getCode());
        if (isNameSame && isCodeSame) {
            throw new AppException(NO_CHANGE_DETECTED);
        }
        if (industryUpdateRequest.getId() == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        if (industryRepo.existsByName(industryUpdateRequest.getName())) {
            throw new AppException(ERROR_EXIST_NAME);
        }
        if (industryRepo.existsByCode(industryUpdateRequest.getCode())) {
            throw new AppException(ERROR_EXIST_CODE);
        }
        if (industryRepo.existsByName(industryUpdateRequest.getName())
                && industryRepo.existsByCode(industryUpdateRequest.getCode())) {
            throw new AppException(ERROR_EXIST_NAME_AND_CODE);
        }
        industry.setName(industryUpdateRequest.getName());
        industry.setCode(industryUpdateRequest.getCode());
        industry.setStatus(industryUpdateRequest.getStatus());
        industryRepo.save(industry);
        IndustryResponse industryResponse = new IndustryResponse(industry);
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industryResponse);
    }

    @Override
    public ApiResponse<Object> inactiveIndustry(Integer id) {
        if (id == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }

        Industry industry = industryRepo.getIndustriesById(id);
        if (industry == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        industry.setStatus(Status.INACTIVE);
        industryRepo.save(industry);
        return new ApiResponse<>(DELETED);
    }
}
