package com.backend.autocarrerbridge.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.AccountRespone.DisplayBusinessDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserBusinessDTO;
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

    UserAccountService userAccountService;
    RoleService roleService;

    @Override
    public DisplayBusinessDTO registerBusiness(UserBusinessDTO userBusinessDTO) {
        if (userBusinessDTO == null) {
            throw new IllegalArgumentException("User business data cannot be null");
        }

        Business existingBusiness = businessRepository.findByEmail(userBusinessDTO.getEmail());
        if (existingBusiness != null) {
            throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
        }

        if (!userBusinessDTO.getPassword().equals(userBusinessDTO.getRePassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }

        if (userBusinessDTO.getLicenseImage() == null
                || userBusinessDTO.getLicenseImage().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_LICENSE);
        }

        Integer licenseImageId;
        try {
            licenseImageId = imageService.uploadFile(userBusinessDTO.getLicenseImage());
            if (licenseImageId == null) {
                throw new AppException(ErrorCode.ERROR_LICENSE);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_LICENSE);
        }

        // Tạo và lưu UserAccount
        UserAccount userAccount = new UserAccount();
        modelMapper.map(userBusinessDTO, userAccount);
        userAccount.setRole(roleService.findById(PredefinedRole.BUSINESS.getValue()));
        userAccount.setUsername(userBusinessDTO.getEmail());
        userAccount.setState(State.PENDING);
        UserAccount savedUserAccount = userAccountService.registerUser(userAccount);

        // Tạo và lưu Business
        Business business = modelMapper.map(userBusinessDTO, Business.class);
        business.setLicenseImageId(licenseImageId);
        business.setUserAccount(savedUserAccount);

        try {
            Business savedBusiness = businessRepository.save(business);
            DisplayBusinessDTO displayBusinessDTO = new DisplayBusinessDTO();
            modelMapper.map(savedUserAccount, displayBusinessDTO);
            modelMapper.map(savedBusiness, displayBusinessDTO);
            return displayBusinessDTO;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_USER);
        }
    }

    @Override
    public Business findByEmail(String email) {
        return businessRepository.findByEmail(email);
    }

    @Override
    public Business updateBusiness(Integer id, Business business) {
        return null;
    }

    @Override
    public List<Business> getListBusiness() {
        return List.of();
    }

    @Override
    public Business getBusinessById(Integer id) {
        return null;
    }

    @Override
    public Business addBusiness(Business request) {
        return null;
    }

    @Override
    public void deleteBusiness(Integer id) {}
}
