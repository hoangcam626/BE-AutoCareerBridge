package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EXIST_INDUSTRY_OF_BUSINESS;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NOT_FOUND_BUSINESS;
import static com.backend.autocarrerbridge.util.Constant.DELETED;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.request.industry.DeleteIndustryRequest;
import com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto;
import com.backend.autocarrerbridge.dto.response.industry.CheckIndustryResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.BusinessIndustry;
import com.backend.autocarrerbridge.entity.Job;
import com.backend.autocarrerbridge.repository.BusinessIndustryRepository;
import com.backend.autocarrerbridge.repository.BusinessRepository;
import com.backend.autocarrerbridge.repository.JobRepository;
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
import com.backend.autocarrerbridge.repository.IndustryRepository;
import com.backend.autocarrerbridge.service.IndustryService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndustryServiceImp implements IndustryService {

    private final IndustryRepository industryRepository;
    private final TokenService tokenService;
    private final BusinessRepository businessRepository;
    private final BusinessIndustryRepository businessIndustryRepository;
    private final JobRepository jobRepository;

    /**
     * Lấy Business từ token
     */
    public Business getBusinessViaToken() throws ParseException {
        // Cắt chuỗi token
        String token = tokenService.getJWT();
        // Lấy username từ token
        String usernameToken = tokenService.getClaim(token, "sub");
        Business businessToken = businessRepository.findByUsername(usernameToken);
        if (businessToken == null) {
            throw new AppException(ERROR_NOT_FOUND_BUSINESS);
        }
        return businessToken;
    }

    /**
     * Lấy username của employee qua token
     */
    public String getUsernameViaToken() throws ParseException {
        String token = tokenService.getJWT();
        // Lấy username từ token
        return tokenService.getClaim(token, "sub");
    }

    /**
     * Phương thức kiếm tra tên ngành và mã ngành đã tồn tại chưa
     */
    private void checkNameAndCodeExistsUpdate(IndustryRequest industryRequest) {
        // Kiểm tra nếu tên ngành đã tồn tại
        if (industryRepository.existsByNameAndIdNot(industryRequest.getName(), industryRequest.getId())) {
            throw new AppException(ErrorCode.ERROR_EXIST_NAME);
        }

        // Kiểm tra nếu mã đã tồn tại
        if (industryRepository.existsByCodeAndIdNot(industryRequest.getCode(), industryRequest.getId())) {
            throw new AppException(ErrorCode.ERROR_EXIST_CODE);
        }
    }

    /**
     * Lấy danh sách ngành nghề phân trang
     */
    @Override
    public ApiResponse<Object> getAllIndustryPaging(int first, int rows, int page, String name, String code) {
        Pageable pageable = PageRequest.of(page, rows);
        Page<IndustryResponse> industryList = industryRepository.getAllIndustryActivePag(name, code, pageable);
        if (industryList.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        IndustryPagingRequest industryPagingRequest =
                new IndustryPagingRequest(industryList.getTotalElements(), industryList.getContent());
        return ApiResponse.builder().data(industryPagingRequest).build();
    }

    /**
     * Lấy danh sách ngành nghề
     */
    @Override
    public ApiResponse<Object> getAllIndustry() {
        List<IndustryResponse> list = industryRepository.getAllIndustryActive();
        if (list.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        return ApiResponse.builder().data(list).build();
    }

    /**
     * Thêm mới ngành nghề
     */
    @Override
    public ApiResponse<Object> createIndustry(IndustryRequest industryRequest) throws ParseException {
        Industry industry = new Industry();
        if (industryRequest.getName() == null || industryRequest.getName().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_EXIST_INDUSTRY);
        }
        // Kiểm tra tên và mã ngành nghề tổn tại chưa
        if (industryRepository.existsByName(industryRequest.getName())) {
            throw new AppException(ErrorCode.ERROR_EXIST_NAME);
        }

        if (industryRepository.existsByCode(industryRequest.getCode())) {
            throw new AppException(ErrorCode.ERROR_EXIST_NAME);
        }

        industry.setName(industryRequest.getName());
        industry.setCode(industryRequest.getCode());
        industry.setStatus(Status.ACTIVE);
        industry.setCreatedBy(getUsernameViaToken());
        industryRepository.save(industry);
        IndustryResponse industryResponse = new IndustryResponse(industry);
        return ApiResponse.builder().data(industryResponse).build();
    }

    /**
     * Cập nhật ngành nghề
     */
    @Override
    public ApiResponse<Object> updateIndustry(IndustryRequest industryRequest) throws ParseException {
        // check id xem có null hay không
        if (industryRequest.getId() == null) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        Industry industry = industryRepository.getIndustriesById(industryRequest.getId());
        if (industry == null) {
            throw new AppException(ErrorCode.ERROR_EXIST_INDUSTRY);
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
            checkNameAndCodeExistsUpdate(industryRequest);
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
        industry.setUpdatedBy(getUsernameViaToken());
        industryRepository.save(industry);
        IndustryResponse industryResponse = new IndustryResponse(industry);
        return ApiResponse.builder().data(industryResponse).build();
    }

    /**
     * Vô hiệu hóa ngành nghề
     */
    @Override
    public ApiResponse<Object> inactiveIndustry(Integer id) throws ParseException {
        // Kiểm tra id có bị trống không
        if (id == null) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        Industry industry = industryRepository.getIndustriesById(id);
        if (industry == null) {
            throw new AppException(ErrorCode.ERROR_EXIST_INDUSTRY);
        }
        // Nếu ngành đã bị vô hiệu hoá thì trả ra thông báo
        if (industry.getStatus() == Status.INACTIVE) {
            throw new AppException(ErrorCode.ERROR_INACTIVE);
        }
        industry.setUpdatedBy(getUsernameViaToken());
        industry.setStatus(Status.INACTIVE);
        industryRepository.save(industry);
        return new ApiResponse<>(DELETED);
    }

    /**
     * Thêm ngành nghề vào doanh nghiệp
     */
    @Override
    public ApiResponse<Object> createIndustryToBusiness(Integer industryId) throws ParseException {
        Business business = businessRepository.getBusinessById(getBusinessViaToken().getId());
        if (business == null) {
            throw new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS);
        }
        if (industryId == null) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        // Kiểm tra ngành nghề đã có ngành nghề chưa
        BusinessIndustry existBusinessIndustry =
                businessIndustryRepository.findByBusinessIdAndIndustryId(business.getId(), industryId);
        Industry industry = industryRepository.getIndustriesById(industryId);

        if (industry == null) {
            throw new AppException(ErrorCode.ERROR_EXIST_INDUSTRY);
        }
        BusinessIndustry newBusinessIndustry;
        // Nếu doanh nghiệp đã có ngành nghề thì trả ra thông báo
        if (existBusinessIndustry != null) {
            throw new AppException(ERROR_EXIST_INDUSTRY_OF_BUSINESS);
        } else {
            newBusinessIndustry = BusinessIndustry.builder()
                    .business(business)
                    .industry(industry)
                    .build();
            newBusinessIndustry.setCreatedBy(getUsernameViaToken());
            newBusinessIndustry.setStatus(Status.ACTIVE);
            businessIndustryRepository.save(newBusinessIndustry);
        }
        BusinessIndustryDto businessIndustryDto = new BusinessIndustryDto(newBusinessIndustry);
        return ApiResponse.builder().data(businessIndustryDto).build();
    }

    @Override
    public ApiResponse<Object> getIndustryOfBusiness(int page, int size, String keyword, Pageable pageable) throws ParseException {
        Page<BusinessIndustryDto> list =
                businessIndustryRepository.getIndustryOfBusiness(getBusinessViaToken().getId(), keyword, pageable);
        PagingResponse<BusinessIndustryDto> pagingResponse = new PagingResponse<>(list);
        return ApiResponse.builder().data(pagingResponse).build();
    }

    @Override
    public ApiResponse<Object> getIndustryOfBusinessNoPag() throws ParseException {
        return ApiResponse.builder()
                .data(businessIndustryRepository.getIndustryOfBusinessNoPag(getBusinessViaToken().getId()))
                .build();
    }

    @Override
    public ApiResponse<Object> getIndustryDetail(Integer industryId) throws ParseException {
        BusinessIndustry businessIndustry =
                businessIndustryRepository.findByBusinessIdAndIndustryId(getBusinessViaToken().getId(), industryId);
        if (businessIndustry == null) {
            throw new AppException(ErrorCode.ERROR_EXIST_INDUSTRY);
        }
        BusinessIndustryDto businessIndustryDto = new BusinessIndustryDto(businessIndustry);
        return ApiResponse.builder().data(businessIndustryDto).build();
    }

    @Override
    public ApiResponse<Object> inactiveIndustryOfBusiness(DeleteIndustryRequest deleteIndustryRequest) {
        if (deleteIndustryRequest.getBusinessIndustryId() == null
                || deleteIndustryRequest.getBusinessIndustryId().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        // Kiểm tra và xóa từng ngành nghề trong danh sách
        List<BusinessIndustry> industriesToDelete =
                businessIndustryRepository.findByIdIn(deleteIndustryRequest.getBusinessIndustryId());
        if (industriesToDelete.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_CODE_NOT_FOUND);
        }
        // Kiểm tra xem có công việc nào đang sử dụng ngành nghề này không
        for (BusinessIndustry businessIndustry : industriesToDelete) {
            Industry industry = businessIndustry.getIndustry();

            // Kiểm tra nếu industry đã được sử dụng trong Job
            List<Job> jobs = jobRepository.findByIndustry(industry);
            if (!jobs.isEmpty()) {
                throw new AppException(ErrorCode.ERROR_CODE_INDUSTRY_IN_USE);
            }
        }
        // Xóa tất cả các ngành nghề tìm thấy
        businessIndustryRepository.deleteAll(industriesToDelete);

        return ApiResponse.builder()
                .data(DELETED)
                .build();
    }

    @Override
    public ApiResponse<Object> checkIndustryExist(Integer industryId) throws ParseException {
        boolean exists = businessIndustryRepository.existsByIndustry_IdAndBusiness_Id(getBusinessViaToken().getId() ,industryId);
        return ApiResponse.builder()
                .data(new CheckIndustryResponse(exists)).build();
    }
}
