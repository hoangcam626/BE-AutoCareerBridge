package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.model.api.AuthenticationResponse;

public interface UserAuthentication {
    AuthenticationResponse authenticate(String username);
}
