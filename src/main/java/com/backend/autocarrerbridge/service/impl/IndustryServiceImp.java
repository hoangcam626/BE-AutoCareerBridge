package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.util.Constant.DELETED;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.industry.IndustryPagingRequest;
import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;
import com.backend.autocarrerbridge.entity.Industry;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.IndustryRepo;
import com.backend.autocarrerbridge.service.IndustryService;
import com.backend.autocarrerbridge.util.Constant;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndustryServiceImp implements IndustryService {

    private final IndustryRepo industryRepo;

    /** Phương thức kiếm tra tên ngành và mã ngành đã tồn tại chưa*/
    private void checkNameAndCodeExists(IndustryRequest industryRequest) {
        if (industryRepo.existsByName(industryRequest.getName())) {
            throw new AppException(ErrorCode.ERROR_EXIST_NAME);
        }
        if (industryRepo.existsByCode(industryRequest.getCode())) {
            throw new AppException(ErrorCode.ERROR_EXIST_CODE);
        }
        if (industryRepo.existsByName(industryRequest.getName())
                && industryRepo.existsByCode(industryRequest.getCode())) {
            throw new AppException(ErrorCode.ERROR_EXIST_NAME_AND_CODE);
        }
    }

    @Override
    public ApiResponse<Object> getAllIndustryPaging(int first, int rows, int page, String name, String code) {
        Pageable pageable = PageRequest.of(page, rows);
        Page<IndustryResponse> industryList = industryRepo.getAllIndustryActivePag(name, code, pageable);
        if (industryList.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        IndustryPagingRequest industryPagingRequest =
                new IndustryPagingRequest(industryList.getTotalElements(), industryList.getContent());
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industryPagingRequest);
    }

    @Override
    public ApiResponse<Object> getAllIndustry() {
        List<IndustryResponse> list = industryRepo.getAllIndustryActive();
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, list);
    }

    @Override
    public ApiResponse<IndustryResponse> createIndustry(IndustryRequest industryRequest) {
        Industry industry = new Industry();
        if (industryRequest.getName() == null || industryRequest.getName().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        // Check tên và mã của ngành nghề đã tồn tại hay chưa
        checkNameAndCodeExists(industryRequest);
        industry.setName(industryRequest.getName());
        industry.setCode(industryRequest.getCode());
        industry.setStatus(Status.ACTIVE);
        industryRepo.save(industry);
        IndustryResponse industryResponse = new IndustryResponse(industry);
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industryResponse);
    }

    @Override
    public ApiResponse<IndustryResponse> updateIndustry(IndustryRequest industryRequest) {
        // check id xem có null hay không
        if (industryRequest.getId() == null) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        Industry industry = industryRepo.getIndustriesById(industryRequest.getId());
        if (industry == null) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }

        // Kiểm tra name, code, status có thay đổi hay không
        boolean isNameChanged = !industry.getName().equals(industryRequest.getName());
        boolean isCodeChanged = !industry.getCode().equals(industryRequest.getCode());
        boolean isStatusChanged = !industry.getStatus().equals(industryRequest.getStatus());

        // Nếu không có thay đổi nào, hiển thị thông báo
        if (!isNameChanged && !isCodeChanged && !isStatusChanged) {
            throw new AppException(ErrorCode.NO_CHANGE_DETECTED);
        }

        // Kiểm tra name và code có bị trùng hay không nếu có thay đổi
        if (isNameChanged || isCodeChanged) {
            checkNameAndCodeExists(industryRequest);
        }

        // Cập nhật ngành nghề
        if (isNameChanged) {
            industry.setName(industryRequest.getName());
        }
        if (isCodeChanged) {
            industry.setCode(industryRequest.getCode());
        }
        if (isStatusChanged) {
            industry.setStatus(industryRequest.getStatus());
        }

        industryRepo.save(industry);
        IndustryResponse industryResponse = new IndustryResponse(industry);
        return new ApiResponse<>(Constant.SUCCESS, Constant.SUCCESS_MESSAGE, industryResponse);
    }

    @Override
    public ApiResponse<Object> inactiveIndustry(Integer id) {
        // Kiểm tra id có bị trống không
        if (id == null) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        Industry industry = industryRepo.getIndustriesById(id);
        if (industry == null) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        // Nếu ngành đã bị vô hiệu hoá thì trả ra thông báo
        if (industry.getStatus() == Status.INACTIVE) {
            throw new AppException(ErrorCode.ERROR_INACTIVE);
        }
        industry.setStatus(Status.INACTIVE);
        industryRepo.save(industry);
        return new ApiResponse<>(DELETED);
    }
}
