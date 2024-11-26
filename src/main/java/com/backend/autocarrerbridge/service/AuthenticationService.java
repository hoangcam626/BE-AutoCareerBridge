package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;
import com.backend.autocarrerbridge.entity.UserAccount;

public interface AuthenticationService {
    AuthenticationResponse authenticate(UserAccount userAccounts) throws ParseException;

    void logout() throws ParseException;

    String getNewToken() throws ParseException;
}
