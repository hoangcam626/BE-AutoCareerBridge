package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;

import com.backend.autocarrerbridge.entity.Location;
import com.backend.autocarrerbridge.service.LocationService;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_FORMAT_PW;

import static com.backend.autocarrerbridge.util.Constant.APPROVED_ACCOUNT;
import static com.backend.autocarrerbridge.util.Constant.REJECTED_ACCOUNT;

import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.university.AdminUniversityResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityTotalResponse;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.converter.UniversityConverter;
import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityApprovedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRejectedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.util.email.EmailCode;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import com.backend.autocarrerbridge.util.enums.Status;

import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.service.RoleService;
import com.backend.autocarrerbridge.service.UniversityService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    RoleService roleService;
    ModelMapper modelMapper;
    UserAccountService userAccountService;
    UniversityRepository universityRepository;
    RedisTemplate<String, String> redisTemplate;
    SendEmail sendEmail;
    ImageService imageService;
    LocationService locationService;

    @Override
    public UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest) {
        checkValidateUniversity(userUniversityRequest);
        if (!Objects.equals(
                redisTemplate.opsForValue().get(userUniversityRequest.getEmail()),
                userUniversityRequest.getVerificationCode())) {
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }
        // Set Role
        Role role = roleService.findById(PredefinedRole.UNIVERSITY.getValue());
        // Tạo UserAccount từ DTO
        UserAccount userAccount = new UserAccount();
        modelMapper.map(userUniversityRequest, userAccount);
        userAccount.setRole(role);
        userAccount.setUsername(userUniversityRequest.getEmail());
        userAccount.setState(State.PENDING);

        // Đăng ký tài khoản
        UserAccount savedUserAccount = userAccountService.registerUser(userAccount);

        // Tạo đối tượng University
        University university = new University();
        university.setUserAccount(savedUserAccount);
        modelMapper.map(userUniversityRequest, university);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setWardId(userUniversityRequest.getWardId());
        locationRequest.setProvinceId(userUniversityRequest.getProvinceId());
        locationRequest.setDistrictId(userUniversityRequest.getDistrictId());
        Location location = locationService.saveLocationLogin(locationRequest);

        // Tạo và lưu Business
        if(Objects.isNull(location)){
            throw new AppException(ErrorCode.ERROR_LOCATION_NOT_FOUND);
        }
        university.setLocation(location);
        // Lưu thông tin đại học vào DB
        universityRepository.save(university);

        // Chuẩn bị đối tượng trả về
        UniversityRegisterResponse universityRegisterResponse = new UniversityRegisterResponse();
        modelMapper.map(savedUserAccount, universityRegisterResponse);
        modelMapper.map(university, universityRegisterResponse);

        return universityRegisterResponse;
    }

    /**
     * Phương thức chấp nhận tài khoản doanh nghiệp
     *
     * @param req - đầu vào chứa ID của doanh nghiệp cần được phê duyệt.
     */
    @Override
    public UniversityApprovedResponse approvedAccount(UniversityApprovedRequest req) {
        University university = findById(req.getId());
        UserAccount userAccount = university.getUserAccount();

        // Phê duyệt tài khoản người dùng thay đổi trạng thái thành "APPROVED".
        userAccountService.approvedAccount(userAccount);

        // Email để thông báo tài khoản đã được phê duyệt.
        EmailDTO emailDTO = new EmailDTO(university.getEmail(), APPROVED_ACCOUNT, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.APPROVED, "");

        return UniversityApprovedResponse.of(Boolean.TRUE);
    }

    /**
     * Phương thức từ chối tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa ID của doanh nghiệp cần bị từ chối.
     */
    @Override
    public UniversityRejectedResponse rejectedAccount(UniversityRejectedRequest req) {
        University university = findById(req.getId());
        UserAccount userAccount = university.getUserAccount();

        // Từ chối tài khoản người thay đổi trạng thái thành "REJECTED".
        userAccountService.rejectedAccount(userAccount);

        //Đổi trạng thái sang INACTIVE (xóa mềm thông tin doanh nghiệp)
        university.setStatus(Status.INACTIVE);
        universityRepository.save(university);

        // Gửi email để thông báo tài khoản đã bị từ chối.
        EmailDTO emailDTO = new EmailDTO(university.getEmail(), REJECTED_ACCOUNT, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.REJECTED, req.getMessage());

        return UniversityRejectedResponse.of(Boolean.TRUE);
    }

    @Transactional
    @Override
    public UniversityResponse update(int id, UniversityRequest universityRequest) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_UNIVERSITY_NOT_FOUND));
        university.setName(universityRequest.getName());
        university.setWebsite(universityRequest.getWebsite());
        university.setFoundedYear(universityRequest.getFoundedYear());
        university.setPhone(universityRequest.getPhone());
        university.setDescription(universityRequest.getDescription());

        if (!Objects.isNull(universityRequest.getLogoImageId()) && !universityRequest.getLogoImageId().isEmpty()) {
            // Tải lên ảnh mới và lưu ID của ảnh
            university.setLogoImageId(imageService.uploadFile(universityRequest.getLogoImageId()));
        }
        LocationRequest locationRequest = LocationRequest.builder()
            .provinceId(universityRequest.getProvinceId())
            .districtId(universityRequest.getDistrictId())
            .wardId(universityRequest.getWardId())
            .build();
        // Save Location and retrieve Location entity
        Location location = locationService.saveLocation(locationRequest);
        university.setLocation(location);
        // Set the location for the university
        universityRepository.save(university);
        return UniversityConverter.convertToResponse(university);

    }

    @Override
    public List<UniversityResponse> getById(int id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ERROR_UNIVERSITY_NOT_FOUND));
        return List.of(UniversityConverter.convertToResponse(university));
    }

    @Override
    public List<UniversityResponse> getAll() {
        List<University> universities = universityRepository.findAll();
        universities.sort(Comparator.comparingLong(University::getId).reversed());
        return universities.stream().map(UniversityConverter::convertToResponse).toList();
    }

    @Override
    public University findById(Integer id) {
        return universityRepository.findById(id).orElse(null);
    }

    @Override
    public List<UniversityResponse> findUniversityByNameOrLocation(String address, String universityName) {
        List<University> list = universityRepository.findUniversity(address, universityName);
        return list.stream().map(university -> modelMapper.map(university,UniversityResponse.class)).toList();
    }

    /**
     * Lấy danh sách các trường đại học phân trang theo trạng thái.
     */
    @Override
    public PagingResponse<AdminUniversityResponse> getPagingByState(PageInfo req, State state) {
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(req.getKeyword());
        Page<University> universities = universityRepository.findAllByState(pageable, state, keyword);
        Page<AdminUniversityResponse> res = universities.map(u -> modelMapper.map(u, AdminUniversityResponse.class));
        return new PagingResponse<>(res);
    }


    /**
     * Lấy danh sách tất cả các trường đại học gồm phân trang và tìm kiếm.
     */
    @Override
    public PagingResponse<AdminUniversityResponse> getAllUniversities(PageInfo req) {
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        String keyword = Validation.escapeKeywordForQuery(req.getKeyword());
        Page<University> universities = universityRepository.findAll(pageable, keyword);
        Page<AdminUniversityResponse> res = universities.map(u -> modelMapper.map(u, AdminUniversityResponse.class));
        return new PagingResponse<>(res);
    }

    @Override
    public EmailCode generateCode(UserUniversityRequest userUniversityRequest) {
        checkValidateUniversity(userUniversityRequest);
        return userAccountService.generateVerificationCode(userUniversityRequest.getEmail());
    }

    @Override
    public AdminUniversityResponse detail(Integer id) {
        University university = findById(id);
        return modelMapper.map(university, AdminUniversityResponse.class);
    }

    @Override
    public List<UniversityTotalResponse> getAllTotalUniversity() {
        return universityRepository.getUniversityTotal();
    }

    public void checkValidateUniversity(UserUniversityRequest userUniversityRequest) {
        if(!Validation.isValidPassword(userUniversityRequest.getPassword())){
            throw  new AppException(ERROR_FORMAT_PW);
        }
        if (Objects.isNull(userUniversityRequest.getPassword())
                || Objects.isNull(userUniversityRequest.getRePassword())
                || !userUniversityRequest.getPassword().equals(userUniversityRequest.getRePassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }

        if (universityRepository.findByPhone(userUniversityRequest.getPhone()) != null) {
            throw new AppException(ErrorCode.ERROR_PHONE_EXIST);
        }

        if (Objects.isNull(userUniversityRequest.getEmail())
                || userUniversityRequest.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }

        if (Objects.isNull(userUniversityRequest.getName())
                || userUniversityRequest.getName().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        if (!Objects.isNull(userAccountService.getUserByUsername(userUniversityRequest.getEmail()))) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }
    }

}
