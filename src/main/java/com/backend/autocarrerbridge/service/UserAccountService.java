package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.*;
import com.backend.autocarrerbridge.dto.useraccount.sdi.UserAccountRegisterSdi;
import com.backend.autocarrerbridge.entity.UserAccount;

public interface UserAccountService {
    DisplayBussinessDTO registerBussiness(UserBussinessDTO userBussinessDTO);
    DisplayUniverSityDTO registerUniversity(UserUniversityDTO userUniversityDTO);
    UserAccount register(UserAccountRegisterSdi req);
    UserAccount approvedAccount(UserAccount userAccount);
    UserAccount getUserById(Integer id);
    DisplayUserAccountDTO login(UserAccountResponeDTO useraccountDTO);
    void saveRefreshToken(Integer id, String refresh_token);
    UserAccount getUserByUserName(String username);
}
