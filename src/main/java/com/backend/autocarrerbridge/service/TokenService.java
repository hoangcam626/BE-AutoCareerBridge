package com.backend.autocarrerbridge.service;


import com.backend.autocarrerbridge.entity.UserAccount;

import java.text.ParseException;

public interface TokenService {
    String generateToken(UserAccount userAccount, int expirationHours);
    boolean verifyToken(String token) throws ParseException;
    long getTimeToLive(String token) throws ParseException;
    String getClaim(String token, String claim) throws ParseException;
    String getSub(String token) throws ParseException;
}