package com.backend.autocarrerbridge.service.impl;

import java.text.ParseException;

import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.service.UserAuthentication;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthenticationImpl implements UserAuthentication {
    private final UserAccountService userAccountService;
    private final AuthenticationService authenticationService;

    @Override
    public AuthenticationResponse authenticate(String username) {
        UserAccount findUserAccount = userAccountService.getUserByUsername(username);
        AuthenticationResponse authenticationResponse;
        try {
            authenticationResponse = authenticationService.authenticate(findUserAccount);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (authenticationResponse.getRefreshToken() != null) {
            userAccountService.saveRefreshTokenForUser(
                    findUserAccount.getId(), authenticationResponse.getRefreshToken());
        }
        return authenticationResponse;
    }
}
