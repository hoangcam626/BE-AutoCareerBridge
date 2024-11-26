package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.entity.UserAccount;
import com.nimbusds.jose.JOSEException;

public interface TokenService {
    String generateToken(UserAccount userAccount, int expirationHours);

    boolean verifyToken(String token) throws ParseException, JOSEException;

    long getTimeToLive(String token) throws ParseException;

    String getClaim(String token, String claim) throws ParseException;

    String getJWT();
}
