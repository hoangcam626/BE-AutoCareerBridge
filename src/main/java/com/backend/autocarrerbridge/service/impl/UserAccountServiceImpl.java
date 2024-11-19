package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.*;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.*;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final BussinessRepository bussinessRepository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UniversityRepository universityRepository;
    @Override
    public DisplayBussinessDTO registerBussiness(UserBussinessDTO userBussinessDTO) {
        if (userBussinessDTO == null) {
            throw new IllegalArgumentException("User business data cannot be null");
        }

        // Check if username already exists
        if (userAccountRepository.findByUsername(userBussinessDTO.getUsername()) != null) {
            throw new AppException(ErrorCode.ERROR_USER);
        }

        try {
            // Check if email already exists
            Business existingBussiness = bussinessRepository.findByEmail(userBussinessDTO.getEmail());
            if (existingBussiness != null) {
                throw new AppException(ErrorCode.ERROR_EMAIL_EXIST);
            }

            // Check password match
            if (!userBussinessDTO.getPassword().equals(userBussinessDTO.getRePassword())) {
                throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
            }

            // Find role by ID
            Role role = roleRepository.findById(userBussinessDTO.getIdRole()).orElse(null);
            if (role == null) {
                throw new AppException(ErrorCode.ERROR_USER);
            }

            // Save user account
            UserAccount savedUserAccount = modelMapper.map(userBussinessDTO, UserAccount.class);
            savedUserAccount.setRole(role);

            String hashedPassword = passwordEncoder.encode(userBussinessDTO.getPassword());
            savedUserAccount.setPassword(hashedPassword);

            UserAccount userAccount = userAccountRepository.save(savedUserAccount);

            // Validate license image
            if (userBussinessDTO.getLicenseImage() == null || userBussinessDTO.getLicenseImage().isEmpty()) {
                throw new AppException(ErrorCode.ERROR_LINCESE);
            }

            Integer licenseImageId = imageService.uploadFile(userBussinessDTO.getLicenseImage());
            if (licenseImageId == null) {
                throw new AppException(ErrorCode.ERROR_LINCESE);
            }

            // Save business information
            Business business = modelMapper.map(userBussinessDTO, Business.class);
            business.setLicenseImageId(licenseImageId);
            business.setUserAccount(userAccount);

            Business savedBusiness = bussinessRepository.save(business);

            modelMapper.map(savedBusiness, userBussinessDTO);
            return modelMapper.map(userBussinessDTO,DisplayBussinessDTO.class);

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
        Role role = roleRepository.findById(userUniversityDTO.getIdRole()).orElse(null);
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
        System.out.println(user);
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
