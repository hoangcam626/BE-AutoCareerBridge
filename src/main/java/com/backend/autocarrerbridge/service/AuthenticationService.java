package com.backend.autocarrerbridge.service;


import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.model.api.IntrospectRequest;
import com.backend.autocarrerbridge.model.api.IntrospectResponse;
import org.apache.catalina.User;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(UserAccount userAccounts) throws ParseException;
    void logout(String token) throws ParseException;
    String getNewToken(String token) throws ParseException;
    IntrospectResponse introspect(IntrospectRequest introspectRequest) throws Exception;
}
