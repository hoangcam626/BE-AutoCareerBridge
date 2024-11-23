package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;

public interface UserAuthentication {
    AuthenticationResponse authenticate(String username);
}
