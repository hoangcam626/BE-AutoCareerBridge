package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;
import static com.backend.autocarrerbridge.util.Constant.DELETED;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.industry.IndustryPaging;
import com.backend.autocarrerbridge.dto.industry.IndustrySdi;
import com.backend.autocarrerbridge.dto.industry.IndustrySdo;
import com.backend.autocarrerbridge.dto.industry.IndustryUpdateSdi;
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
        Page<IndustrySdo> industryList = industryRepo.getAllIndustryActivePag(name, code, pageable);
        if (industryList.isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        IndustryPaging industryPaging = new IndustryPaging(industryList.getTotalElements(), industryList.getContent());
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industryPaging);
    }

    @Override
    public ApiResponse<Object> getAllIndustry() {
        List<IndustrySdo> list = industryRepo.getAllIndustryActive();
        if (list.isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, list);
    }

    @Override
    public ApiResponse<IndustrySdo> createIndustry(IndustrySdi industrySdi) {
        Industry industry = new Industry();
        if (industrySdi.getName() == null || industrySdi.getName().isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        if (industryRepo.existsByName(industrySdi.getName())) {
            throw new AppException(ERROR_EXIST_NAME);
        }
        if (industryRepo.existsByCode(industrySdi.getCode())) {
            throw new AppException(ERROR_EXIST_CODE);
        }
        if (industryRepo.existsByName(industrySdi.getName()) && industryRepo.existsByCode(industrySdi.getCode())) {
            throw new AppException(ERROR_EXIST_NAME_AND_CODE);
        }
        industry.setName(industrySdi.getName());
        industry.setCode(industrySdi.getCode());
        industry.setStatus(Status.ACTIVE);
        industryRepo.save(industry);
        IndustrySdo industrySdo = new IndustrySdo(industry);
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industrySdo);
    }

    @Override
    public ApiResponse<IndustrySdo> updateIndustry(IndustryUpdateSdi industryUpdateSdi) {
        Industry industry = industryRepo.getIndustriesById(industryUpdateSdi.getId());

        if (industry == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        boolean isNameSame = industry.getName().equals(industryUpdateSdi.getName());
        boolean isCodeSame = industry.getCode().equals(industryUpdateSdi.getCode());
        if (isNameSame && isCodeSame) {
            throw new AppException(NO_CHANGE_DETECTED);
        }
        if (industryUpdateSdi.getId() == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        if (industryRepo.existsByName(industryUpdateSdi.getName())) {
            throw new AppException(ERROR_EXIST_NAME);
        }
        if (industryRepo.existsByCode(industryUpdateSdi.getCode())) {
            throw new AppException(ERROR_EXIST_CODE);
        }
        if (industryRepo.existsByName(industryUpdateSdi.getName())
                && industryRepo.existsByCode(industryUpdateSdi.getCode())) {
            throw new AppException(ERROR_EXIST_NAME_AND_CODE);
        }
        industry.setName(industryUpdateSdi.getName());
        industry.setCode(industryUpdateSdi.getCode());
        industry.setStatus(industryUpdateSdi.getStatus());
        industryRepo.save(industry);
        IndustrySdo industrySdo = new IndustrySdo(industry);
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industrySdo);
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
