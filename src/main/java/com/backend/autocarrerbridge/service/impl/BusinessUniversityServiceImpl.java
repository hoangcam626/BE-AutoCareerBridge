package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.converter.SentRequestConverter;
import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.cooperation.CooperationUniversityResponse;
import com.backend.autocarrerbridge.dto.response.cooperation.SentRequestResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.BusinessUniversity;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.mapper.BusinessUniversityMapper;
import com.backend.autocarrerbridge.repository.BusinessRepository;
import com.backend.autocarrerbridge.repository.BusinessUniversityRepository;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.service.BusinessUniversityService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.util.Constant;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_APPROVED_RELATION;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_CANCEL_RELATION;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_CODE_NOT_FOUND;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EXIST_RELATION;
import static com.backend.autocarrerbridge.util.Constant.SUB;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BusinessUniversityServiceImpl implements BusinessUniversityService {

    TokenService tokenService;
    BusinessRepository businessRepository;
    UniversityRepository universityRepository;
    BusinessUniversityRepository businessUniversityRepository;
    SentRequestConverter sentRequestConverter;
    BusinessUniversityMapper businessUniversityMapper;


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
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        return businessToken;
    }

    public University getUniversityFromToken() throws ParseException {
        var emailAccountLogin = tokenService.getClaim(tokenService.getJWT(),SUB);
        University university=universityRepository.findByEmail(emailAccountLogin);
        if(Objects.isNull(university))
            throw new AppException(ErrorCode.ERROR_NOT_FOUND_UNIVERSITY);
        return university;
    }

    /**
     * Lấy ra danh sách những yêu cầu đã gửi
     */
    @Override
    public ApiResponse<Object> getSentRequest() throws ParseException {
        List<BusinessUniversity> universities =
                businessUniversityRepository.getSentRequestOfBusiness(getBusinessViaToken().getId());
        if (universities.isEmpty()) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        List<SentRequestResponse> sentRequestResponse = sentRequestConverter.toSentRequestResponse(universities);
        return ApiResponse.builder()
                .data(sentRequestResponse)
                .build();
    }

    /**
     * Doanh nghiệp gửi yêu cầu hợp tác đến trường học
     */
    @Override
    public ApiResponse<Object> sendRequest(Integer universityId) throws ParseException {
        Business business = businessRepository.getBusinessById(getBusinessViaToken().getId());
        if (business == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        if (universityId == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        University university = universityRepository.getUniversityById(universityId);
        if (university == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Check thông tin trong bảng BusinessUniversity
        BusinessUniversity existingRelation =
                businessUniversityRepository.findByBusinessIdAndUniversityId(business.getId(), universityId);

        // Nếu doanh nghiệp và trường học đã hợp tác
        if (existingRelation != null) {
            //Nếu trạng thái hợp tác của doanh nghiệp và trường học là Approved thì hiển thị thông báo đã hợp tác
            if (existingRelation.getStatusConnected().equals(State.APPROVED)) {
                throw new AppException(ERROR_APPROVED_RELATION);
            }
            //Nếu trạng thái hợp tác của doanh nghiệp và trường học là Rejected thì gửi lại yêu cầu
            if (existingRelation.getStatusConnected().equals(State.REJECTED)) {
                existingRelation.setStatusConnected(State.PENDING);
                existingRelation.setUpdatedBy(business.getUserAccount().getUsername());
                businessUniversityRepository.save(existingRelation);
            }
            throw new AppException(ERROR_EXIST_RELATION);
        } else {
            // Tạo mới yêu cầu hợp tác nếu chưa tồn tại
            BusinessUniversity newRelation = BusinessUniversity.builder()
                    .business(business)
                    .university(university)
                    .statusConnected(State.PENDING)
                    .build();
            newRelation.setStatus(Status.ACTIVE);
            newRelation.setCreatedBy(business.getUserAccount().getUsername());
            businessUniversityRepository.save(newRelation);
        }
        return ApiResponse.builder()
                .data(Constant.SEND_REQUEST_SUCCESS)
                .build();
    }

    /**
     * Doanh nghiệp hủy yêu cầu hợp tác
     */
    @Override
    public ApiResponse<Object> cancelRequest(Integer universityId) throws ParseException {
        BusinessUniversity businessUniversity =
                businessUniversityRepository.findByBusinessIdAndUniversityId(getBusinessViaToken().getId(), universityId);
        if (businessUniversity == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        if(universityId == null){
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Nếu trạng thái của yêu cầu là active thì đổi thành inactive
        if (businessUniversity.getStatus().equals(Status.ACTIVE)) {
            businessUniversity.setStatus(Status.INACTIVE);
            businessUniversity.setUpdatedBy(getBusinessViaToken().getUserAccount().getUsername());
            businessUniversityRepository.save(businessUniversity);
        } else {
            throw new AppException(ERROR_CANCEL_RELATION);
        }
        return ApiResponse.builder()
                .data(Constant.CANCELED_SUCCESSFUL)
                .build();
    }

    // lấy ra tất cả hợp tác cả active và inactive
    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversity() throws ParseException {
        List<BusinessUniversity> list=businessUniversityRepository.getAllRequestOfUniversity(getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse=list.stream().map(businessUniversityMapper::toCooperationUniversityResponse).toList();
        if(listResponse.isEmpty()){
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    // lấy ra tất cả hợp tác đang chờ
    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversityPending() throws ParseException {
        List<BusinessUniversity> list=businessUniversityRepository.getBusinessUniversityPending(getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse=list.stream().map(businessUniversityMapper::toCooperationUniversityResponse).toList();
        if(listResponse.isEmpty()){
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    // lấy ra tất cả hợp tác đã chấp thuận
    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversityApprove() throws ParseException {
        List<BusinessUniversity> list=businessUniversityRepository.getBusinessUniversityApprove(getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse=list.stream().map(businessUniversityMapper::toCooperationUniversityResponse).toList();
        if(listResponse.isEmpty()){
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    // lấy ra tất cả hợp tác đã hủy
    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversityReject() throws ParseException {
        List<BusinessUniversity> list=businessUniversityRepository.getBusinessUniversityReject(getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse=list.stream().map(businessUniversityMapper::toCooperationUniversityResponse).toList();
        if(listResponse.isEmpty()){
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    @Override
    public void approveRequetCooperation(Integer idBusinessUniversity) {
        BusinessUniversity businessUniversity = businessUniversityRepository.findBusinessUniversityById(idBusinessUniversity);
        if(Objects.isNull(businessUniversity))
            throw new AppException(ErrorCode.NOT_FOUNDED);
        businessUniversity.setStatusConnected(State.APPROVED);
        businessUniversityRepository.save(businessUniversity);
    }
}
