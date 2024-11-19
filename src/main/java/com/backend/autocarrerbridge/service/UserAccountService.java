package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.AccountRespone.*;
import com.backend.autocarrerbridge.entity.UserAccount;

public interface UserAccountService {
    DisplayBusinessDTO registerBusiness(UserBusinessDTO userBusinessDTO);
    DisplayUniverSityDTO registerUniversity(UserUniversityDTO userUniversityDTO);
    UserAccount getUserById(Integer id);
    DisplayUserAccountDTO login(UserAccountResponeDTO useraccountDTO);
    void saveRefreshToken(Integer id, String refresh_token);
    UserAccount getUserByUserName(String username);
}
