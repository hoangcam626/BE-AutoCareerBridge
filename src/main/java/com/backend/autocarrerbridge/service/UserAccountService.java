package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.*;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.dto.request.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.UserAccountResponse;

public interface UserAccountService {
    DisplayBussinessDTO registerBussiness(UserBussinessDTO userBussinessDTO);
    DisplayUniverSityDTO registerUniversity(UserUniversityDTO userUniversityDTO);
    UserAccount getUserById(Integer id);
    DisplayUserAccountDTO login(UserAccountResponeDTO useraccountDTO);
    void saveRefreshToken(Integer id, String refresh_token);
    UserAccount getUserByUserName(String username);
    UserAccountResponse createUser(UserAccountRequest request);
}
