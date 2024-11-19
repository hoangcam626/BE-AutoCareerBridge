package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.AccountRespone.*;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.*;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.OrganizationType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountServiceImpl implements UserAccountService {
     UserAccountRepository userAccountRepository;
     BusinessRepository businessRepository;
     ModelMapper modelMapper;
     ImageService imageService;
     PasswordEncoder passwordEncoder;
     RoleRepository roleRepository;
     UniversityRepository universityRepository;
    @Override
    public DisplayBusinessDTO registerBusiness(UserBusinessDTO userBusinessDTO) {
        if (userBusinessDTO == null) {
            throw new IllegalArgumentException("User business data cannot be null");
        }

        // Check if username already exists
        if (userAccountRepository.findByUsername(userBusinessDTO.getUsername()) != null) {
            throw new AppException(ErrorCode.ERROR_USER);
        }

        try {
            // Check if email already exists
            Business existingBussiness = businessRepository.findByEmail(userBusinessDTO.getEmail());
            if (existingBussiness != null) {
                throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
            }

            // Check password match
            if (!userBusinessDTO.getPassword().equals(userBusinessDTO.getRePassword())) {
                throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
            }

            Role role = roleRepository.findById(OrganizationType.BUSINESS.getValue()).orElse(null);
            if (role == null) {
                throw new AppException(ErrorCode.ERROR_USER);
            }

            // Save user account
            UserAccount savedUserAccount = modelMapper.map(userBusinessDTO, UserAccount.class);
            savedUserAccount.setRole(role);

            String hashedPassword = passwordEncoder.encode(userBusinessDTO.getPassword());
            savedUserAccount.setPassword(hashedPassword);

            UserAccount userAccount = userAccountRepository.save(savedUserAccount);

            // Validate license image
            if (userBusinessDTO.getLicenseImage() == null || userBusinessDTO.getLicenseImage().isEmpty()) {
                throw new AppException(ErrorCode.ERROR_LINCESE);
            }

            Integer licenseImageId = imageService.uploadFile(userBusinessDTO.getLicenseImage());
            if (licenseImageId == null) {
                throw new AppException(ErrorCode.ERROR_LINCESE);
            }

            // Save business information
            Business business = modelMapper.map(userBusinessDTO, Business.class);
            business.setLicenseImageId(licenseImageId);
            business.setUserAccount(userAccount);

            Business savedBusiness = businessRepository.save(business);

            modelMapper.map(savedBusiness, userBusinessDTO);
            return modelMapper.map(userBusinessDTO, DisplayBusinessDTO.class);

        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new AppException(ErrorCode.ERROR_NO_CONTENT);
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_USER);
        }
    }

    @Override
    public DisplayUniverSityDTO registerUniversity(UserUniversityDTO userUniversityDTO) {
        if(universityRepository.findByEmail(userUniversityDTO.getEmail()) != null) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }
        if(universityRepository.findByPhone(userUniversityDTO.getPhone()) != null) {
            throw new AppException(ErrorCode.ERROR_PHONE_EXIST);
        }
        Role role = roleRepository.findById(OrganizationType.UNIVERSITY.getValue()).orElse(null);
        if (role == null) {
            throw new AppException(ErrorCode.ERROR_USER);
        }
        if (!userUniversityDTO.getPassword().equals(userUniversityDTO.getRePassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }
        UserAccount savedUserAccount = modelMapper.map(userUniversityDTO, UserAccount.class);
        savedUserAccount.setRole(role);

        String hashedPassword = passwordEncoder.encode(userUniversityDTO.getPassword());
        savedUserAccount.setPassword(hashedPassword);

        UserAccount userAccount = userAccountRepository.save(savedUserAccount);
        University university = new University();
        university.setEmail(userUniversityDTO.getEmail());
        university.setPhone(userUniversityDTO.getPhone());
        university.setName(userUniversityDTO.getName());
        university.setUserAccount(userAccount);
        universityRepository.save(university);
        return modelMapper.map(userUniversityDTO,DisplayUniverSityDTO.class);
    }

    @Override
    public UserAccount getUserById(Integer id) {
        return userAccountRepository.findById(id).orElse(null);
    }

    @Override
    public DisplayUserAccountDTO login(UserAccountResponeDTO useraccountDTO) {
        if (useraccountDTO.getUsername() == null || useraccountDTO.getPassword() == null) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        UserAccount user = userAccountRepository.findByUsername(useraccountDTO.getUsername());
        if (user == null) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        if (passwordEncoder.matches(useraccountDTO.getPassword(), user.getPassword())) {
            UserAccountResponeDTO userAccountResponeDTO = new UserAccountResponeDTO();
            userAccountResponeDTO.setStatus(user.getStatus());
            userAccountResponeDTO.setId(user.getId());
            userAccountResponeDTO.setUsername(user.getUsername());
            userAccountResponeDTO.setPassword(user.getPassword());
            userAccountResponeDTO.setRole(modelMapper.map(user.getRole(), RoleDTO.class));

            return  modelMapper.map(userAccountResponeDTO,DisplayUserAccountDTO.class);
        } else {
            throw new AppException(ErrorCode.ERROR_PASSWORD_INCORRECT);
        }
    }

    @Override
    public void saveRefreshToken(Integer id, String refresh_token) {
        UserAccount userAccounts = userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userAccounts.setRefreshToken(refresh_token);
        userAccountRepository.save(userAccounts);
    }

    @Override
    public UserAccount getUserByUserName(String username) {
        return userAccountRepository.findByUsername(username);
    }
}
