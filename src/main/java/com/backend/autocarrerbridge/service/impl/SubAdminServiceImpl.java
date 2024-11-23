package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;

import java.text.ParseException;
import java.util.List;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminCreateRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminDeleteRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminSelfRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminUpdateRequest;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminCreateResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminDeleteResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminSelfResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminUpdateResponse;
import com.backend.autocarrerbridge.emailconfig.EmailDTO;
import com.backend.autocarrerbridge.emailconfig.SendEmail;
import com.backend.autocarrerbridge.entity.SubAdmin;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.SubAdminRepository;
import com.backend.autocarrerbridge.service.*;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import com.backend.autocarrerbridge.util.password.PasswordGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SubAdminServiceImpl implements SubAdminService {

    private final SubAdminRepository subAdminRepository;
    private final UserAccountService userAccountService;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private final SendEmail sendEmail;

    public SubAdminCreateResponse create(SubAdminCreateRequest req) throws ParseException {

        validateCreate(req);

        // save image
        var subAdmin = modelMapper.map(req, SubAdmin.class);
        var imgId = imageService.uploadFile(req.getSubAdminImage());
        subAdmin.setSubAdminImageId(imgId);

        // create password & create account
        PasswordGenerator pw = new PasswordGenerator(8, 12);
        String password = pw.generatePassword();
        UserAccount newAccount = new UserAccount();
        newAccount.setUsername(req.getEmail());
        newAccount.setPassword(password);
        newAccount.setState(State.APPROVED);
        newAccount.setRole(roleService.findById(PredefinedRole.SUB_ADMIN.getValue()));
        subAdmin.setUserAccount(userAccountService.registerUser(newAccount));

        // get jwt, get username login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        var nameAccountLogin = tokenService.getClaim(jwt.getTokenValue(), "sub");
        subAdmin.setCreatedBy(nameAccountLogin);

        // save sub-admin
        subAdmin = subAdminRepository.save(subAdmin);

        // send mail with sub-admin account
        EmailDTO emailDTO = new EmailDTO(subAdmin.getEmail(), "Tài Khoản Của Bạn", "");
        sendEmail.sendAccount(emailDTO, password);

        return SubAdminCreateResponse.of(subAdmin.getId());
    }

    public SubAdminUpdateResponse update(SubAdminUpdateRequest req) throws ParseException {

        var subAdmin = getSubAdmin(req.getId());

        boolean isSameAddress = subAdmin.getAddress().equals(req.getAddress());
        boolean isSamePhone = subAdmin.getPhone().equals(req.getPhone());
        boolean isNullImage =
                req.getSubAdminImage() == null || req.getSubAdminImage().isEmpty();

        if (isSameAddress && isSamePhone && isNullImage) {
            throw new AppException(NO_CHANGE_DETECTED);
        }
        if (!isSameAddress) {
            subAdmin.setAddress(req.getAddress());
        }
        if (!isSamePhone) {
            subAdmin.setPhone(req.getPhone());
        }

        if (isNullImage) {
            var imgId = imageService.uploadFile(req.getSubAdminImage());
            subAdmin.setSubAdminImageId(imgId);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        var nameAccountLogin = tokenService.getClaim(jwt.getTokenValue(), "sub");
        subAdmin.setUpdatedBy(nameAccountLogin);

        subAdminRepository.save(subAdmin);
        return SubAdminUpdateResponse.of(Boolean.TRUE);
    }

    public SubAdminSelfResponse self(SubAdminSelfRequest req) {
        var subAdmin = getSubAdmin(req.getId());
        return modelMapper.map(subAdmin, SubAdminSelfResponse.class);
    }

    public SubAdminDeleteResponse delete(SubAdminDeleteRequest req) {
        var subAdmin = getSubAdmin(req.getId());
        subAdmin.setStatus(Status.INACTIVE);
        subAdminRepository.save(subAdmin);
        return SubAdminDeleteResponse.of(Boolean.TRUE);
    }

    public Page<SubAdminSelfResponse> pageSubAdmins(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<SubAdmin> subAdmins = subAdminRepository.findAllPageable(pageable);
        return subAdmins.map(subAdmin -> modelMapper.map(subAdmin, SubAdminSelfResponse.class));
    }

    @Override
    public List<SubAdminSelfResponse> listSubAdmins() {
        List<SubAdmin> subAdmins = subAdminRepository.findAllByStatus();
        return subAdmins.stream()
                .map(subAdmin -> modelMapper.map(subAdmin, SubAdminSelfResponse.class))
                .toList();
    }

    public SubAdmin getSubAdmin(Integer id) {
        return subAdminRepository.findById(id).orElseThrow(() -> new AppException(ERROR_NOT_FOUND_SUB_ADMIN));
    }

    public void validateCreate(SubAdminCreateRequest req) {
        if (!Validation.isValidEmail(req.getEmail())) {
            throw new AppException(ERROR_VALID_EMAIL);
        }
        if (subAdminRepository.existsBySubAdminCode(req.getSubAdminCode())) {
            throw new AppException(ERROR_SUB_ADMIN_CODE_EXIST);
        }
        if (subAdminRepository.existsByEmail(req.getEmail())) {
            throw new AppException(ERROR_EMAIL_EXIST);
        }
    }
}
