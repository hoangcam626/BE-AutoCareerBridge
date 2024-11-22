package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.service.TokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl  implements TokenService{

    @Value("${jwt.signerKey}")
    private String signerKey;

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public String generateToken(UserAccount userAccount, int expirationHours) {
        try {
            if (userAccount == null || userAccount.getUsername() == null || userAccount.getRole() == null) {
                throw new IllegalArgumentException("Invalid user account details for token generation");
            }

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userAccount.getUsername())
                    .jwtID(UUID.randomUUID().toString())
                    .issuer("AutoCarrer")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))
                    .claim("scope", userAccount.getRole().getName())
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(new MACSigner(signerKey.getBytes()));

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Token generation failed",e);
        }
    }

    @Override
    public boolean verifyToken(String token) {
        try {

            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

            return signedJWT.verify(verifier);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    @Override
    public long getTimeToLive(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(stripBearerPrefix(token));
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            Date expirationTime = claimsSet.getExpirationTime();
            if (expirationTime == null) {

                return 0;
            }

            return Math.max((expirationTime.getTime() - System.currentTimeMillis()) / (1000 * 60), 0);
        } catch (ParseException e) {

            return 0;
        }
    }

    @Override
    public String getClaim(String token, String claim) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(stripBearerPrefix(token));
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            return claimsSet.getStringClaim(claim);
        } catch (Exception e) {

            throw new RuntimeException("Claim retrieval failed", e);
        }
    }

    @Override
    public String getSub(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(stripBearerPrefix(token));
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            return claimsSet.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Subject retrieval failed", e);
        }
    }

    private String stripBearerPrefix(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        return token;
    }
}
