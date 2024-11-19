package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.UserAccountResponse;

public interface UserAccountService {
    UserAccountResponse createUser(UserAccountRequest request);
}
