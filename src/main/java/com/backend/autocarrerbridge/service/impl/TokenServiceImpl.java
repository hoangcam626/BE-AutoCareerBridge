package com.backend.autocarrerbridge.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.service.TokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;

import static com.backend.autocarrerbridge.util.Constant.APPLICATION_NAME;
import static com.backend.autocarrerbridge.util.Constant.SCOPE;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.signerKey}")
    private String jwtKey;



    @Override
    public String generateToken(UserAccount userAccount, int expirationHours) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userAccount.getUsername())
                .jwtID(UUID.randomUUID().toString())
                .issuer(APPLICATION_NAME)
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(expirationHours, ChronoUnit.DAYS).toEpochMilli()))
                .claim(SCOPE, userAccount.getRole().getName())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }
    }

    @Override
    public boolean verifyToken(String token) throws ParseException, JOSEException {
        JWSVerifier verifier = new MACVerifier(jwtKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.verify(verifier);
    }

    @Override
    public long getTimeToLive(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        Date issueTime = claimsSet.getIssueTime();
        Date expirationTime = claimsSet.getExpirationTime();
        return (expirationTime.getTime() - issueTime.getTime()) / (1000 * 60);
    }

    @Override
    public String getClaim(String token, String claim) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        return claimsSet.getStringClaim(claim);
    }

    @Override
    public String getJWT(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getTokenValue();
    }
}
