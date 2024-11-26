package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
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
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.backend.autocarrerbridge.util.Constant.APPROVED;
import static com.backend.autocarrerbridge.util.Constant.REJECTED;

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


    @Override
    public UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest) {

        // Kiểm tra password và rePassword có khớp không
        if (userUniversityRequest.getPassword() == null
                || userUniversityRequest.getRePassword() == null
                || !userUniversityRequest.getPassword().equals(userUniversityRequest.getRePassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }

        if (universityRepository.findByPhone(userUniversityRequest.getPhone()) != null) {
            throw new AppException(ErrorCode.ERROR_PHONE_EXIST);
        }

        if (userUniversityRequest.getEmail() == null
                || userUniversityRequest.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }
        if (!Objects.equals(
                redisTemplate.opsForValue().get(userUniversityRequest.getEmail()),
                userUniversityRequest.getVerificationCode())) {
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }
        if (userUniversityRequest.getName() == null
                || userUniversityRequest.getName().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        // Kiểm tra xem email đã được đăng ký trước đó hay chưa
        if (userAccountService.getUserByUsername(userUniversityRequest.getEmail()) != null) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
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
    public void approvedAccount(UniversityApprovedRequest req){
        University university = findById(req.getId());
        UserAccount userAccount = university.getUserAccount();

        // Phê duyệt tài khoản người dùng thay đổi trạng thái thành "APPROVED".
        userAccountService.approvedAccount(userAccount);

        // Email để thông báo tài khoản đã được phê duyệt.
        EmailDTO emailDTO = new EmailDTO(university.getEmail(), APPROVED, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.APPROVED);
    }

    /**
     * Phương thức từ chối tài khoản doanh nghiệp.
     *
     * @param req Yêu cầu chứa ID của doanh nghiệp cần bị từ chối.
     */
    @Override
    public void rejectedAccount(UniversityRejectedRequest req){
        University university = findById(req.getId());
        UserAccount userAccount = university.getUserAccount();

        // Từ chối tài khoản người thay đổi trạng thái thành "REJECTED".
        userAccountService.rejectedAccount(userAccount);

        //Đổi trạng thái sang INACTIVE (xóa mềm thông tin doanh nghiệp)
        university.setStatus(Status.INACTIVE);
        universityRepository.save(university);

        // Gửi email để thông báo tài khoản đã bị từ chối.
        EmailDTO emailDTO = new EmailDTO(university.getEmail(), REJECTED, "");
        sendEmail.sendAccountStatusNotification(emailDTO, State.REJECTED);
    }
    @Override
    public University findById(Integer id) {
        return universityRepository.findById(id).orElse(null);
    }
}
