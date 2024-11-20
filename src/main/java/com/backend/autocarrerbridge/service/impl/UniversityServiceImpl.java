package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.AccountRespone.DisplayUniverSityDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserUniversityDTO;
import com.backend.autocarrerbridge.emailconfig.Email;
import com.backend.autocarrerbridge.emailconfig.SendEmail;
import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.UniversityRepository;
import com.backend.autocarrerbridge.service.RoleService;
import com.backend.autocarrerbridge.service.UniversityService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.OrganizationType;
import com.backend.autocarrerbridge.util.enums.State;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    RoleService roleService;
    ModelMapper modelMapper;
    UserAccountService userAccountService;
    UniversityRepository universityRepository;
    SendEmail sendEmail;
    @Override
    public DisplayUniverSityDTO registerUniversity(UserUniversityDTO userUniversityDTO) {
        // Kiểm tra password và rePassword có khớp không
        if (userUniversityDTO.getPassword() == null || userUniversityDTO.getRePassword() == null
                || !userUniversityDTO.getPassword().equals(userUniversityDTO.getRePassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }

        if(universityRepository.findByPhone(userUniversityDTO.getPhone()) != null){
            throw new AppException(ErrorCode.ERROR_PHONE_EXIST);
        }

        if (userUniversityDTO.getEmail() == null || userUniversityDTO.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }

        if (userUniversityDTO.getName() == null || userUniversityDTO.getName().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        // Kiểm tra xem email đã được đăng ký trước đó hay chưa
        if (userAccountService.getUserByUserName(userUniversityDTO.getEmail()) != null) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }
        // Set Role
         Role role =  roleService.findById(OrganizationType.UNIVERSITY.getValue());
        // Tạo UserAccount từ DTO
        UserAccount userAccount = new UserAccount();
        modelMapper.map(userUniversityDTO, userAccount);
        userAccount.setRole(role);
        userAccount.setUsername(userUniversityDTO.getEmail());

        // Đăng ký tài khoản
        UserAccount savedUserAccount = userAccountService.register(userAccount);

        // Tạo đối tượng University
        University university = new University();
        university.setUserAccount(savedUserAccount);
        modelMapper.map(userUniversityDTO, university);

        // Lưu thông tin đại học vào DB
        universityRepository.save(university);

        // Chuẩn bị đối tượng trả về
        DisplayUniverSityDTO displayUniverSityDTO = new DisplayUniverSityDTO();
        modelMapper.map(savedUserAccount, displayUniverSityDTO);
        modelMapper.map(university, displayUniverSityDTO);
//        Email email = new Email(displayUniverSityDTO.getEmail(),"Thông báo về yêu cầu hợp tác ","");
//        sendEmail.sendEmail(email,displayUniverSityDTO.getName());
        return displayUniverSityDTO;
    }

    @Override
    public University findByEmail(String email) {
        return universityRepository.findByEmail(email);
    }


}

