package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.AccountRespone.*;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.*;

import com.backend.autocarrerbridge.service.UserAccountService;

import com.backend.autocarrerbridge.util.enums.State;
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
     ModelMapper modelMapper;
     PasswordEncoder passwordEncoder;

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
            if(user.getState().equals(State.PENDING)){
                throw new AppException(ErrorCode.ERROR_USER_PENDING);
            }
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

    @Override
    public UserAccount register(UserAccount userAccount) {
        UserAccount newAccount = UserAccount.builder()
                .username(userAccount.getUsername())
                .password(passwordEncoder.encode(userAccount.getPassword()))
                .role(userAccount.getRole())
                .state(State.PENDING)
                .build();
        return userAccountRepository.save(newAccount);
    }
}
