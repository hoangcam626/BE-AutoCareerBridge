package com.backend.autocarrerbridge.service;


import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(UserAccount userAccounts) throws ParseException;
    void logout() throws ParseException;
    String getNewToken() throws ParseException;
}
