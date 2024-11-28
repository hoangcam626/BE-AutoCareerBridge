package com.backend.autocarrerbridge.service.impl;

import java.util.List;
import java.util.Objects;

import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.request.location.LocationRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.mapper.BusinessMapper;
import com.backend.autocarrerbridge.mapper.LocationMapper;
import com.backend.autocarrerbridge.service.LocationService;
import com.backend.autocarrerbridge.util.enums.Status;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.business.BusinessApprovedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessRejectedRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessRegisterResponse;
import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
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

import static com.backend.autocarrerbridge.util.Constant.APPROVED;
import static com.backend.autocarrerbridge.util.Constant.REJECTED;

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
        if (userBusinessRequest == null) {
            throw new IllegalArgumentException("User business data cannot be null");
        }

        // Kiểm tra xem email doanh nghiệp đã tồn tại chưa
        Business existingBusiness = businessRepository.findByEmail(userBusinessRequest.getEmail());
        if (existingBusiness != null) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }

        // Xác thực mật khẩu
        if (!userBusinessRequest.getPassword().equals(userBusinessRequest.getRePassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }

        if (userBusinessRequest.getLicenseImage() == null
                || userBusinessRequest.getLicenseImage().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_LICENSE);
        }
        if (!Objects.equals(
                redisTemplate.opsForValue().get(userBusinessRequest.getEmail()),
                userBusinessRequest.getVerificationCode())) {
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }
        Integer licenseImageId;
        try {
            licenseImageId = imageService.uploadFile(userBusinessRequest.getLicenseImage());
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

        // Tạo và lưu Business
        Business business = modelMapper.map(userBusinessRequest, Business.class);
        business.setLicenseImageId(licenseImageId);
        business.setUserAccount(savedUserAccount);

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

        // Kiểm tra email mới có trùng với doanh nghiệp khác không
        if (!businessUpdate.getEmail().equals(request.getEmail())) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }

        // Cập nhật thông tin doanh nghiệp từ request
        businessMapper.updateBusiness(businessUpdate, request);
        LocationRequest locationRequest = LocationRequest.builder()
                .id(businessUpdate.getLocation().getId())
                .description(request.getDescriptionLocation())
                .provinceId(request.getProvinceId())
                .districtId(request.getDistrictId())
                .wardId(request.getWardId())
                .build();
        Location location= locationService.saveLocation(locationRequest);

        LocationResponse locationResponse = locationMapper.toLocationResponse(location);

        // set ảnh cho business
        businessUpdate.setBusinessImageId(imageService.uploadFile(request.getBusinessImage()));
        businessUpdate.setLicenseImageId(imageService.uploadFile(request.getLicenseImage()));

        BusinessResponse businessResponse=businessMapper.toBusinessResponse(businessRepository.save(businessUpdate));
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
     *
     * @param req - đầu vào chứa ID của doanh nghiệp cần được phê duyệt.
     */
    @Override
    public void approvedAccount(BusinessApprovedRequest req){
        Business business = getBusinessById(req.getId());
        UserAccount userAccount = business.getUserAccount();

        // Phê duyệt tài khoản người dùng thay đổi trạng thái thành "APPROVED".
        userAccountService.approvedAccount(userAccount);

        // Email để thông báo tài khoản đã được phê duyệt.
        EmailDTO emailDTO = new EmailDTO(business.getEmail(), APPROVED, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.APPROVED);
    }

    /**
     * Phương thức từ chối tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa ID của doanh nghiệp cần bị từ chối.
     */
    @Override
    public void rejectedAccount(BusinessRejectedRequest req){
        Business business = getBusinessById(req.getId());
        UserAccount userAccount = business.getUserAccount();

        // Từ chối tài khoản người thay đổi trạng thái thành "REJECTED".
        userAccountService.rejectedAccount(userAccount);

        //Đổi trạng thái sang INACTIVE (xóa mềm thông tin doanh nghiệp)
        business.setStatus(Status.INACTIVE);
        businessRepository.save(business);

        // Gửi email để thông báo tài khoản đã bị từ chối.
        EmailDTO emailDTO = new EmailDTO(business.getEmail(), REJECTED, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.REJECTED);
    }


}
