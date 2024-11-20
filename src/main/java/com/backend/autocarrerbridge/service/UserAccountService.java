package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.AccountRespone.*;
import com.backend.autocarrerbridge.entity.UserAccount;

public interface UserAccountService {
    DisplayUserAccountDTO login(UserAccountResponeDTO useraccountDTO);
    void saveRefreshToken(Integer id, String refresh_token);
    UserAccount getUserByUserName(String username);
    UserAccount register(UserAccount userAccount);
}
