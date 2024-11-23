package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
     RoleService roleService;
     ModelMapper modelMapper;
     UserAccountService userAccountService;
     UniversityRepository universityRepository;
     RedisTemplate<String, String> redisTemplate;
    @Override
    public UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest) {

        // Kiểm tra password và rePassword có khớp không
        if (userUniversityRequest.getPassword() == null || userUniversityRequest.getRePassword() == null
                || !userUniversityRequest.getPassword().equals(userUniversityRequest.getRePassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }

        if(universityRepository.findByPhone(userUniversityRequest.getPhone()) != null){
            throw new AppException(ErrorCode.ERROR_PHONE_EXIST);
        }

        if (userUniversityRequest.getEmail() == null || userUniversityRequest.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }
        if(!Objects.equals(redisTemplate.opsForValue().get(userUniversityRequest.getEmail()), userUniversityRequest.getVerificationCode())){
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }
        if (userUniversityRequest.getName() == null || userUniversityRequest.getName().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        // Kiểm tra xem email đã được đăng ký trước đó hay chưa
        if (userAccountService.getUserByUsername(userUniversityRequest.getEmail()) != null) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }


        // Set Role
         Role role =  roleService.findById(PredefinedRole.UNIVERSITY.getValue());
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


}

