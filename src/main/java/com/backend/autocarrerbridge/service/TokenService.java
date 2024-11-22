package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.entity.UserAccount;

public interface TokenService {
    String generateToken(UserAccount userAccount, int expirationHours);

    boolean verifyToken(String token) throws Exception;

    long getTimeToLive(String token) throws ParseException;

    String getClaim(String token, String claim) throws ParseException;

    String getSub(String token) throws ParseException;
}
