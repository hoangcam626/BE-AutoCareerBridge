package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.PageInfoDTO;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminCreateSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminDeleteSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminSelfSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminUpdateSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminCreateSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminDeleteSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminSelfSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminUpdateSdo;
import com.backend.autocarrerbridge.dto.useraccount.sdi.UserAccountRegisterSdi;
import com.backend.autocarrerbridge.entity.SubAdmin;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.SubAdminRepository;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.SubAdminService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.DataUtil;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.util.enums.Status;
import com.backend.autocarrerbridge.util.password.PasswordGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SubAdminServiceImpl implements SubAdminService {

    private final SubAdminRepository subAdminRepository;
    private final UserAccountService userAccountService;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;


    public SubAdminCreateSdo create(SubAdminCreateSdi req) throws ParseException {

        validateCreate(req);

        var subAdmin = modelMapper.map(req, SubAdmin.class);
        var imgId = imageService.uploadFile(req.getSubAdminImage());
        subAdmin.setSubAdminImageId(imgId);

        PasswordGenerator pw = new PasswordGenerator(8, 12);
        String password = pw.generatePassword();
        var newAccount = userAccountService.register(UserAccountRegisterSdi.of(req.getEmail(), password, "Sub-admin"));
        subAdmin.setUserAccount(newAccount);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        var nameAccountLogin = tokenService.getClaim(jwt.getTokenValue(), "sub");
        subAdmin.setCreatedBy(nameAccountLogin);

        subAdmin = subAdminRepository.save(subAdmin);
        return SubAdminCreateSdo.of(subAdmin.getId());
    }


    public SubAdminUpdateSdo update(SubAdminUpdateSdi req) throws ParseException {

        var subAdmin = getSubAdmin(req.getId());
        subAdmin = modelMapper.map(req, SubAdmin.class);
        var imgId = imageService.uploadFile(req.getSubAdminImage());
        subAdmin.setSubAdminImageId(imgId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        var nameAccountLogin = tokenService.getClaim(jwt.getTokenValue(), "sub");
        subAdmin.setUpdatedBy(nameAccountLogin);

        subAdmin = subAdminRepository.save(subAdmin);
        return SubAdminUpdateSdo.of(Boolean.TRUE);

    }

    public SubAdminSelfSdo self(SubAdminSelfSdi req) {
        var subAdmin = getSubAdmin(req.getId());
        return modelMapper.map(subAdmin, SubAdminSelfSdo.class);
    }

    public SubAdminDeleteSdo delete(SubAdminDeleteSdi req) {
        var subAdmin = getSubAdmin(req.getId());
        subAdmin.setStatus(Status.INACTIVE);
        subAdminRepository.save(subAdmin);
        return SubAdminDeleteSdo.of(Boolean.TRUE);
    }

    public Page<SubAdminSelfSdo> pageSubAdmins(PageInfoDTO req) {
        Pageable pageable = PageRequest.of(req.getCurrentPage(), req.getPageSize());
        Page<SubAdmin> subAdmins = subAdminRepository.findAll(pageable);
        return subAdmins.map(subAdmin -> modelMapper.map(subAdmin, SubAdminSelfSdo.class));
    }

    @Override
    public List<SubAdminSelfSdo> listSubAdmins() {
        List<SubAdmin> subAdmins = subAdminRepository.findAll();
        return subAdmins.stream()
                .map(subAdmin -> modelMapper.map(subAdmin, SubAdminSelfSdo.class))
                .collect(Collectors.toList());
    }

    public SubAdmin getSubAdmin(Integer id) {
        return subAdminRepository.findById(id).orElseThrow(() -> new AppException(ERROR_NOT_FOUND_SUB_ADMIN));
    }

    public void validateCreate(SubAdminCreateSdi req) {
        if (!Validation.isValidEmail(req.getEmail())) {
            throw new AppException(ERROR_INVALID_EMAIL);
        }
        if (subAdminRepository.existsByEmail(req.getEmail())) {
            throw new AppException(ERROR_EMAIL_EXIST);
        }
    }
}
