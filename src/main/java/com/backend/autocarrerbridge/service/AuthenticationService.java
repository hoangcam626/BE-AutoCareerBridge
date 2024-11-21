package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.model.api.IntrospectRequest;
import com.backend.autocarrerbridge.model.api.IntrospectResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(UserAccount userAccounts) throws ParseException;

    void logout(String token) throws ParseException;

    String getNewToken(String token) throws ParseException;

    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws Exception;
}
