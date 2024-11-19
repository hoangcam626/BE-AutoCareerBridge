package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.UserAccountResponse;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.mapper.UserAccountMapper;
import com.backend.autocarrerbridge.repository.UserAccountRepository;
import com.backend.autocarrerbridge.service.UserAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountImpl implements UserAccountService {

    UserAccountRepository userAccountRepository;
    UserAccountMapper userAccountMapper;

    @Override
    public UserAccountResponse createUser(UserAccountRequest request) {
        UserAccount userAccount=userAccountMapper.toUserAccount(request);
        try {
            userAccount=userAccountRepository.save(userAccount);
        }catch (DataIntegrityViolationException exception){
            throw new AppException(ErrorCode.ERROR_USER_EXITED);
        }
        return userAccountMapper.toUserAccountResponse(userAccount);
    }


}
