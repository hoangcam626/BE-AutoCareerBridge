package com.backend.autocarrerbridge.service.impl;

import java.util.List;

import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.mapper.BusinessMapper;
import com.backend.autocarrerbridge.service.LocationService;
import com.backend.autocarrerbridge.util.enums.Status;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessRegisterResponse;
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
    LocationService locationService;

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

        // Kiểm tra và xử lý hình ảnh giấy phép
        if (userBusinessRequest.getLicenseImage() == null || userBusinessRequest.getLicenseImage().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_LICENSE);
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

        // Tạo tài khoản người dùng cho doanh nghiệp
        UserAccount userAccount = UserAccount.builder()
                .username(userBusinessRequest.getEmail()) // Sử dụng email doanh nghiệp làm username
                .password("default_password") // Mật khẩu mặc định
                .role(roleService.findById(PredefinedRole.BUSINESS.getValue())) // Gán role BUSINESS
                .state(State.PENDING) // Trạng thái chờ phê duyệt
                .build();

        UserAccount savedUserAccount = userAccountService.registerUser(userAccount); // Đăng ký tài khoản

        // Tạo đối tượng Business và lưu vào DB
        Business business = Business.builder()
                .name(userBusinessRequest.getName())
                .email(userBusinessRequest.getEmail())
                .licenseImageId(licenseImageId)
                .userAccount(savedUserAccount)
                .build();

        try {
            Business savedBusiness = businessRepository.save(business);
            return modelMapper.map(savedBusiness, BusinessRegisterResponse.class); // Chuyển đổi sang DTO
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_USER); // Xử lý ngoại lệ nếu lưu thất bại
        }
    }


    //Lấy doanh nghiệp theo email.
    @Override
    public Business findByEmail(String email) {
        return businessRepository.findByEmail(email);
    }

    //Cập nhật thông tin doanh nghiệp.
    @Transactional
    @Override
    public BusinessResponse updateBusiness(Integer id, BusinessUpdateRequest request) {
        Business businessUpdate = businessRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));

        // Kiểm tra email mới có trùng với doanh nghiệp khác không
        if (!businessUpdate.getEmail().equals(request.getEmail())) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }

        // Cập nhật thông tin doanh nghiệp từ request
        businessMapper.udpateBusiness(businessUpdate, request);
        businessUpdate.setLocation(locationService.saveLocation(request.getLocationRequest()));

        return businessMapper.toBusinessResponse(businessRepository.save(businessUpdate)); // Lưu và trả về DTO
    }

    //Lấy danh sách tất cả doanh nghiệp.
    @Override
    public List<BusinessResponse> getListBusiness() {
        var businessList = businessRepository.findAll();
        return businessList.stream()
                .map(businessMapper::toBusinessResponse)
                .toList(); // Chuyển đổi danh sách sang DTO
    }

    //Lấy doanh nghiệp theo ID.
    @Override
    public Business getBusinessById(Integer id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));
    }

    //Lấy doanh nghiệp theo ID dưới dạng DTO.
    @Override
    public BusinessResponse getBusinessResponseById(Integer id) {
        var business = businessRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));
        return businessMapper.toBusinessResponse(business);
    }

    //Xóa (deactivate) doanh nghiệp.
    @Override
    @Transactional
    public void deleteBusiness(Integer id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_NOT_FOUND_BUSINESS));

        business.getUserAccount().setStatus(Status.INACTIVE); // Đổi trạng thái
        businessRepository.save(business); // Lưu thay đổi
    }
}

