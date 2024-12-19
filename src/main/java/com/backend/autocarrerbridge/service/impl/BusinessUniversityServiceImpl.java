package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;
import static com.backend.autocarrerbridge.util.Constant.APPROVE_COOPERATION_MESSAGE;

import com.backend.autocarrerbridge.dto.request.cooperation.CooperationApproveRequest;
import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.cooperation.CooperationApproveResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.service.NotificationService;
import com.backend.autocarrerbridge.service.TokenService;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
import com.backend.autocarrerbridge.util.Constant;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor // Tự động tạo constructor cho các trường được đánh dấu final
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true) // Thiết lập phạm vi mặc định là private và các trường là final
public class BusinessUniversityServiceImpl implements BusinessUniversityService {

    // Khai báo các thành phần cần thiết trong service
    TokenService tokenService; // Dịch vụ xử lý token
    BusinessRepository businessRepository; // Repository để thao tác với bảng Business
    UniversityRepository universityRepository; // Repository để thao tác với bảng University
    BusinessUniversityRepository businessUniversityRepository; // Repository để thao tác với bảng BusinessUniversity
    SentRequestConverter sentRequestConverter; // Bộ chuyển đổi đối tượng SentRequest
    BusinessUniversityMapper businessUniversityMapper; // Mapper để chuyển đổi giữa các DTO và Entity
    SendEmail sendEmail;
    NotificationService notificationService;

    /**
     * Lấy thông tin doanh nghiệp dựa vào token
     */
    public Business getBusinessViaToken() throws ParseException {
        String token = tokenService.getJWT(); // Lấy token JWT
        String usernameToken = tokenService.getClaim(token, "sub"); // Lấy thông tin username từ token
        Business businessToken =
                businessRepository.findByUsername(usernameToken); // Lấy thông tin doanh nghiệp từ database
        if (businessToken == null) { // Kiểm tra nếu không tìm thấy
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        return businessToken;
    }

    /**
     * Lấy thông tin trường đại học từ token
     */
    public University getUniversityFromToken() throws ParseException {
        var emailAccountLogin = tokenService.getClaim(tokenService.getJWT(), Constant.SUB);
        University university = universityRepository.findByEmail(emailAccountLogin);
        if (Objects.isNull(university)) { // Kiểm tra nếu không tìm thấy
            throw new AppException(ErrorCode.ERROR_NOT_FOUND_UNIVERSITY);
        }
        return university;
    }

    /**
     * Lấy danh sách các yêu cầu đã gửi từ doanh nghiệp
     */
    @Override
    public ApiResponse<Object> getSentRequest() throws ParseException {
        List<BusinessUniversity> universities = businessUniversityRepository.getSentRequestOfBusiness(
                getBusinessViaToken().getId());
        if (universities.isEmpty()) { // Nếu danh sách trống
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        List<SentRequestResponse> sentRequestResponse = sentRequestConverter.toSentRequestResponse(universities);
        return ApiResponse.builder().data(sentRequestResponse).build();
    }

    /**
     * Doanh nghiệp gửi yêu cầu hợp tác đến trường học
     */
    @Override
    public ApiResponse<Object> sendRequest(Integer universityId) throws ParseException {
        Business business =
                businessRepository.getBusinessById(getBusinessViaToken().getId());
        if (business == null || universityId == null) { // Kiểm tra các thông tin đầu vào
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        University university = universityRepository.getUniversityById(universityId);
        if (university == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Kiểm tra thông tin trong bảng BusinessUniversity
        BusinessUniversity existingRelation =
                businessUniversityRepository.findByBusinessIdAndUniversityId(business.getId(), universityId);

        // Nếu đã tồn tại mối quan hệ hợp tác
        if (existingRelation != null) {
            // Nếu đã được phê duyệt
            if (existingRelation.getStatusConnected().equals(State.APPROVED)) {
                throw new AppException(ERROR_APPROVED_RELATION);
            }
            // Nếu bị từ chối, gửi lại yêu cầu
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
        return ApiResponse.builder().data(Constant.SEND_REQUEST_SUCCESS).build();
    }

    /**
     * Doanh nghiệp hủy yêu cầu hợp tác
     */
    @Override
    public ApiResponse<Object> cancelRequest(Integer universityId) throws ParseException {
        BusinessUniversity businessUniversity = businessUniversityRepository.findByBusinessIdAndUniversityId(
                getBusinessViaToken().getId(), universityId);
        if (businessUniversity == null || universityId == null) {
            throw new AppException(ERROR_CODE_NOT_FOUND);
        }
        // Nếu yêu cầu đang ở trạng thái active, đổi thành inactive
        if (businessUniversity.getStatus().equals(Status.ACTIVE)) {
            businessUniversity.setStatus(Status.INACTIVE);
            businessUniversity.setUpdatedBy(
                    getBusinessViaToken().getUserAccount().getUsername());
            businessUniversityRepository.save(businessUniversity);
        } else {
            throw new AppException(ERROR_CANCEL_RELATION);
        }
        return ApiResponse.builder().data(Constant.CANCELED_SUCCESSFUL).build();
    }

    // Các phương thức lấy danh sách hợp tác theo trạng thái
    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversity() throws ParseException {
        List<BusinessUniversity> list = businessUniversityRepository.getAllRequestOfUniversity(
                getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse = list.stream()
                .map(businessUniversityMapper::toCooperationUniversityResponse)
                .toList();
        if (listResponse.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversityPending() throws ParseException {
        List<BusinessUniversity> list = businessUniversityRepository.getBusinessUniversityPending(
                getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse = list.stream()
                .map(businessUniversityMapper::toCooperationUniversityResponse)
                .toList();
        if (listResponse.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversityApprove() throws ParseException {
        List<BusinessUniversity> list = businessUniversityRepository.getBusinessUniversityApprove(
                getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse = list.stream()
                .map(businessUniversityMapper::toCooperationUniversityResponse)
                .toList();
        if (listResponse.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    @Override
    public List<CooperationUniversityResponse> getAllCooperationOfUniversityReject() throws ParseException {
        List<BusinessUniversity> list = businessUniversityRepository.getBusinessUniversityReject(
                getUniversityFromToken().getId());
        List<CooperationUniversityResponse> listResponse = list.stream()
                .map(businessUniversityMapper::toCooperationUniversityResponse)
                .toList();
        if (listResponse.isEmpty()) {
            throw new AppException(ErrorCode.ERROR_LIST_EMPTY);
        }
        return listResponse;
    }

    /**
     * Phê duyệt yêu cầu hợp tác
     */
    @Override
    public CooperationApproveResponse approveRequestCooperation(CooperationApproveRequest request) throws ParseException {

        BusinessUniversity businessUniversity =
                businessUniversityRepository.findBusinessUniversityById(request.getIdCooperation());
        if (Objects.isNull(businessUniversity)) { // Nếu không tìm thấy
            throw new AppException(ErrorCode.NOT_FOUNDED);
        }
        businessUniversity.setStatusConnected(State.APPROVED);// Chuyển trạng thái sang phê duyệt
        businessUniversityRepository.save(businessUniversity);

        //Gửi thông báo mail đến business
        String emailBusiness = businessUniversityRepository.getEmailBusinessFromIdCooperation(request.getIdCooperation());
        String nameUniversity;
        try {
            nameUniversity = getUniversityFromToken().getName();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String message = nameUniversity + APPROVE_COOPERATION_MESSAGE;
        EmailDTO emailDTO = new EmailDTO(emailBusiness, message, "");
        sendEmail.sendApprovedCooperationNotification(emailDTO, message, nameUniversity);

        //Thông báo bên giao diện
        NotificationResponse notificationResponse = notificationService.send(
                NotificationSendRequest.of(Collections.singletonList(emailBusiness), "Hợp tác được chấp thuận", message)
        );
        return CooperationApproveResponse.of(Boolean.TRUE, notificationResponse);
    }

    @Override
    public void rejectRequestCooperation(Integer idBusinesUniversityReject) {
        BusinessUniversity businessUniversity =
                businessUniversityRepository.findBusinessUniversityById(idBusinesUniversityReject);
        if (Objects.isNull(businessUniversity)) { // Nếu không tìm thấy
            throw new AppException(ErrorCode.NOT_FOUNDED);
        }
        businessUniversity.setStatusConnected(State.REJECTED); // Chuyển trạng thái sang phê duyệt
        businessUniversityRepository.save(businessUniversity); // Chuyển trạng thái sang từ chối
    }

    @Override
    public PagingResponse<CooperationUniversityResponse> gegetAllCooperationOfUniversityPage(String keyword, Pageable pageable) throws ParseException {
        String emailLogin = getUniversityFromToken().getEmail();
        //lấy dữ liệu phân trang từ repo
        var cooperations = businessUniversityRepository.getCooperationForPaging(emailLogin, keyword, pageable);
        //Chuyển đổi danh sách từ entity sang respone
        Page<CooperationUniversityResponse> list = cooperations.map(businessUniversityMapper::toCooperationUniversityResponse);
        return new PagingResponse<>(list);
    }

    @Override
    public PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityPendingPage(String keyword, Pageable pageable) throws ParseException {
        String emailLogin = getUniversityFromToken().getEmail();
        //lấy dữ liệu phân trang từ repo
        var cooperations = businessUniversityRepository.getCooperationPendingForPaging(emailLogin, keyword, pageable);
        //Chuyển đổi danh sách từ entity sang respone
        Page<CooperationUniversityResponse> list = cooperations.map(businessUniversityMapper::toCooperationUniversityResponse);
        return new PagingResponse<>(list);
    }

    @Override
    public PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityApprovePage(String keyword, Pageable pageable) throws ParseException {
        String emailLogin = getUniversityFromToken().getEmail();
        //lấy dữ liệu phân trang từ repo
        var cooperations = businessUniversityRepository.getCooperationApproveForPaging(emailLogin, keyword, pageable);
        //Chuyển đổi danh sách từ entity sang respone
        Page<CooperationUniversityResponse> list = cooperations.map(businessUniversityMapper::toCooperationUniversityResponse);
        return new PagingResponse<>(list);
    }

    @Override
    public PagingResponse<CooperationUniversityResponse> getAllCooperationOfUniversityRejectPage(String keyword, Pageable pageable) throws ParseException {
        String emailLogin = getUniversityFromToken().getEmail();
        //lấy dữ liệu phân trang từ repo
        var cooperations = businessUniversityRepository.getCooperationRejectForPaging(emailLogin, keyword, pageable);
        //Chuyển đổi danh sách từ entity sang respone
        Page<CooperationUniversityResponse> list = cooperations.map(businessUniversityMapper::toCooperationUniversityResponse);
        return new PagingResponse<>(list);
    }
}
