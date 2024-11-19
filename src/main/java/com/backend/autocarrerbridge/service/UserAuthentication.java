package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.UserAccountResponeDTO;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;

public interface UserAuthentication {
    AuthenticationResponse authenticate(String username);
}
