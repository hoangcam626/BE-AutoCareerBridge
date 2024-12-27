package com.backend.autocarrerbridge.service.impl;

import java.util.List;
import java.util.Objects;

import com.backend.autocarrerbridge.dto.response.business.BusinessListHome;
import com.backend.autocarrerbridge.dto.response.business.BusinessSearchPage;
import com.backend.autocarrerbridge.dto.response.business.IntroduceBusiness;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessApprovedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessRejectedRequest;
import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.business.BusinessApprovedResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessRejectedResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessRegisterResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.mapper.BusinessMapper;
import com.backend.autocarrerbridge.mapper.LocationMapper;
import com.backend.autocarrerbridge.service.LocationService;
import com.backend.autocarrerbridge.util.email.EmailCode;
import com.backend.autocarrerbridge.util.enums.Status;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.BusinessRepository;
import com.backend.autocarrerbridge.service.BusinessService;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.RoleService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_FORMAT_PW;
import static com.backend.autocarrerbridge.util.Constant.APPROVED_ACCOUNT;
import static com.backend.autocarrerbridge.util.Constant.REJECTED_ACCOUNT;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {
    BusinessRepository businessRepository;
    ImageService imageService;
    ModelMapper modelMapper;
    BusinessMapper businessMapper;
    UserAccountService userAccountService;
    RoleService roleService;
    SendEmail sendEmail;
    LocationService locationService;
    RedisTemplate<String, String> redisTemplate;
    LocationMapper locationMapper;

    //Đăng ký doanh nghiệp mới.
    @Transactional
    @Override
    public BusinessRegisterResponse registerBusiness(UserBusinessRequest userBusinessRequest) {

        checkValidateRegister(userBusinessRequest);
        if (userBusinessRequest.getVerificationCode() == null) {
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }
        if (!Objects.equals(
                redisTemplate.opsForValue().get(userBusinessRequest.getEmail()),
                userBusinessRequest.getVerificationCode())) {
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }
        Integer licenseImageId;
        try {
            licenseImageId = imageService.uploadFile(userBusinessRequest.getLogoImage());
            if (licenseImageId == null) {
                throw new AppException(ErrorCode.ERROR_LICENSE);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_LICENSE);
        }
        // Tạo và lưu UserAccount
        UserAccount userAccount = new UserAccount();
        modelMapper.map(userBusinessRequest, userAccount);
        userAccount.setRole(roleService.findById(PredefinedRole.BUSINESS.getValue()));
        userAccount.setUsername(userBusinessRequest.getEmail());
        userAccount.setState(State.PENDING);
        UserAccount savedUserAccount = userAccountService.registerUser(userAccount);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setWardId(userBusinessRequest.getWardId());
        locationRequest.setProvinceId(userBusinessRequest.getProvinceId());
        locationRequest.setDistrictId(userBusinessRequest.getDistrictId());
        Location location = locationService.saveLocationLogin(locationRequest);
        // Tạo và lưu Business
        if(Objects.isNull(location)){
            throw new AppException(ErrorCode.ERROR_LOCATION_NOT_FOUND);
        }
        Business business = modelMapper.map(userBusinessRequest, Business.class);
        business.setBusinessImageId(licenseImageId);
        business.setUserAccount(savedUserAccount);
        business.setLocation(location);

        try {
            Business savedBusiness = businessRepository.save(business);
            BusinessRegisterResponse businessRegisterResponse = new BusinessRegisterResponse();
            modelMapper.map(savedUserAccount, businessRegisterResponse);
            modelMapper.map(savedBusiness, businessRegisterResponse);
            return businessRegisterResponse;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_USER);
        }
    }

    // Lấy doanh nghiệp theo email.
    @Override
    public Business findByEmail(String email) {
        return businessRepository.findByEmail(email);
    }

    // Cập nhật thông tin doanh nghiệp.
    @Transactional
    @Override
    public BusinessResponse updateBusiness(Integer id, BusinessUpdateRequest request) {
        Business businessUpdate =
                businessRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));

        // Cập nhật thông tin doanh nghiệp từ request
        businessMapper.updateBusiness(businessUpdate, request);
        LocationRequest locationRequest = LocationRequest.builder()
                .description(request.getDescriptionLocation())
                .provinceId(request.getProvinceId())
                .districtId(request.getDistrictId())
                .wardId(request.getWardId())
                .build();
        if(Objects.nonNull(businessUpdate.getLocation()))
            locationRequest.setId(businessUpdate.getLocation().getId());
        Location location = locationService.saveLocation(locationRequest);

        // set ảnh cho business
        if(Objects.nonNull(request.getBusinessImage()))
            businessUpdate.setBusinessImageId(imageService.uploadFile(request.getBusinessImage()));
        if(Objects.nonNull(request.getLicenseImage()))
            businessUpdate.setLicenseImageId(imageService.uploadFile(request.getLicenseImage()));

        //Lưu location entity vào db
        businessUpdate.setLocation(location);
        BusinessResponse businessResponse = businessMapper.toBusinessResponse(businessRepository.save(businessUpdate));

        LocationResponse locationResponse = locationMapper.toLocationResponse(location);
        businessResponse.setLocation(locationResponse);
        return businessResponse; // Lưu và trả về DTO
    }

    // Lấy danh sách tất cả doanh nghiệp.
    @Override
    public List<BusinessResponse> getListBusiness() {
        var businessList = businessRepository.findAll();
        return businessList.stream().map(businessMapper::toBusinessResponse).toList(); // Chuyển đổi danh sách sang DTO
    }

    // Lấy doanh nghiệp theo ID.
    @Override
    public Business getBusinessById(Integer id) {
        return businessRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));
    }

    // Lấy doanh nghiệp theo ID dưới dạng DTO.
    @Override
    public BusinessResponse getBusinessResponseById(Integer id) {
        var business =
                businessRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));
        return businessMapper.toBusinessResponse(business);
    }

    // Xóa (deactivate) doanh nghiệp.
    @Override
    @Transactional
    public void deleteBusiness(Integer id) {
        Business business =
                businessRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));

        business.getUserAccount().setStatus(Status.INACTIVE); // Đổi trạng thái
        businessRepository.save(business); // Lưu thay đổi
    }

    /**
     * Phương thức chấp nhận tài khoản doanh nghiệp
     */
    @Override
    public BusinessApprovedResponse approvedAccount(BusinessApprovedRequest req) {
        Business business = getBusinessById(req.getId());
        UserAccount userAccount = business.getUserAccount();

        // Phê duyệt tài khoản người dùng thay đổi trạng thái thành "APPROVED".
        userAccountService.approvedAccount(userAccount);

        // Email để thông báo tài khoản đã được phê duyệt.
        EmailDTO emailDTO = new EmailDTO(business.getEmail(), APPROVED_ACCOUNT, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.APPROVED, "");

        return BusinessApprovedResponse.of(Boolean.TRUE);
    }

    /**
     * Phương thức từ chối tài khoản doanh nghiệp.
     */
    @Override
    public BusinessRejectedResponse rejectedAccount(BusinessRejectedRequest req) {
        Business business = getBusinessById(req.getId());
        UserAccount userAccount = business.getUserAccount();

        // Từ chối tài khoản người thay đổi trạng thái thành "REJECTED".
        userAccountService.rejectedAccount(userAccount);

        // Đổi trạng thái sang INACTIVE (xóa mềm thông tin doanh nghiệp)
        business.setStatus(Status.INACTIVE);
        businessRepository.save(business);

        // Gửi email để thông báo tài khoản đã bị từ chối.
        EmailDTO emailDTO = new EmailDTO(business.getEmail(), REJECTED_ACCOUNT, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.REJECTED, req.getMessage());

        return BusinessRejectedResponse.of(Boolean.TRUE);
    }

    /**
     * Phương thức lấy danh sách các doanh nghiệp theo trạng thái và keyword tìm kiếm
     */
    @Override
    public PagingResponse<BusinessResponse> getPagingByState(PageInfo req, State state) {
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(req.getKeyword());
        Page<Business> businesses = businessRepository.findAllByState(pageable, state, keyword  );
        Page<BusinessResponse> res = businesses.map(b ->
                modelMapper.map(b, BusinessResponse.class));
        return new PagingResponse<>(res);
    }

    /**
     * Phương thức lấy danh sách tất cả các doanh nghiệp và tìm kiếm
     */
    @Override
    public PagingResponse<BusinessResponse> getAllBusinesses(PageInfo req) {
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(req.getKeyword());
        Page<Business> businesses = businessRepository.findAll(pageable, keyword);
        Page<BusinessResponse> res = businesses.map(b ->
                modelMapper.map(b, BusinessResponse.class));
        return new PagingResponse<>(res);
    }


    @Override
    public EmailCode generateEmailCode(UserBusinessRequest userBusinessRequest) {
        checkValidateRegister(userBusinessRequest);
        return userAccountService.generateVerificationCode(userBusinessRequest.getEmail());
    }

    @Override
    public List<IntroduceBusiness> getFeatureBusiness(Integer industryId,Pageable page) {
        return businessRepository.getBusinessFeaturedByIndustry(industryId,page);
    }

    @Override
    public List<BusinessListHome> getBusinessListHome() {
        return businessRepository.getListBusinessFollowNumberJob();
    }

    @Override
    public PagingResponse<BusinessSearchPage> getAllBusinessPage(String keyword, Pageable pageable) {
        String keywordValidate = Validation.escapeKeywordForQuery(keyword);
        Page<BusinessSearchPage> businessSearchPages = businessRepository.getBusinessForPaging(keywordValidate, pageable);
        return new PagingResponse<>(businessSearchPages);
    }


    public void checkValidateRegister(UserBusinessRequest userBusinessRequest) {
        if (Objects.isNull(userBusinessRequest)) {
            throw new AppException(ErrorCode.ERROR_NO_CONTENT);
        }
            if (!Validation.isValidPassword(userBusinessRequest.getPassword())) {
                throw new AppException(ERROR_FORMAT_PW);
            }
            if (!Validation.isValidPassword(userBusinessRequest.getPassword())) {
                throw new AppException(ERROR_FORMAT_PW);
            }
            // Kiểm tra xem email doanh nghiệp đã tồn tại chưa
            Business existingBusiness = businessRepository.findByEmail(userBusinessRequest.getEmail());
            if (!Objects.isNull(existingBusiness)) {
                throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
            }
        if (!Objects.isNull(businessRepository.findByTaxCode(userBusinessRequest.getTaxCode()))) {
            throw new AppException(ErrorCode.ERROR_TAX_EXIST);
        }
            // Xác thực mật khẩu
            if (!userBusinessRequest.getPassword().equals(userBusinessRequest.getRePassword())) {
                throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
            }

            if (Objects.isNull(userBusinessRequest.getLogoImage())
                    || userBusinessRequest.getLogoImage().isEmpty()) {
                throw new AppException(ErrorCode.ERROR_LICENSE);
            }
        }
    }
