package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminCreateSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminDeleteSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminSelfSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminUpdateSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminCreateSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminDeleteSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminSelfSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminUpdateSdo;
import com.backend.autocarrerbridge.emailconfig.Email;
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
    private final RoleService roleService;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private final SendEmail sendEmail;


    public SubAdminCreateSdo create(SubAdminCreateSdi req) throws ParseException {

        validateCreate(req);

        //save image
        var subAdmin = modelMapper.map(req, SubAdmin.class);
        var imgId = imageService.uploadFile(req.getSubAdminImage());
        subAdmin.setSubAdminImageId(imgId);

        //create password & create account
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

        //save sub-admin
        subAdmin = subAdminRepository.save(subAdmin);

        //send mail with sub-admin account
        Email email = new Email(subAdmin.getEmail(), "Tài Khoản Của Bạn", "");
        sendEmail.sendAccount(email, password);

        return SubAdminCreateSdo.of(subAdmin.getId());
    }


    public SubAdminUpdateSdo update(SubAdminUpdateSdi req) throws ParseException {

        var subAdmin = getSubAdmin(req.getId());

        boolean isSameAddress = subAdmin.getAddress().equals(req.getAddress());
        boolean isSamePhone = subAdmin.getPhone().equals(req.getPhone());
        boolean isNullImage = req.getSubAdminImage() == null && req.getSubAdminImage().isEmpty();

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

    public Page<SubAdminSelfSdo> pageSubAdmins(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
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
