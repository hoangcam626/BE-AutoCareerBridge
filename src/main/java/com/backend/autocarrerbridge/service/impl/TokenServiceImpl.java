package com.backend.autocarrerbridge.service.impl;


import com.backend.autocarrerbridge.dto.UseraccountDTO;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.service.TokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;
    private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Override
    public String generateToken(UserAccount userAccount, int expirationHours) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userAccount.getUsername())
                .jwtID(UUID.randomUUID().toString())
                .issuer("AutoCarrer")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(expirationHours, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", userAccount.getRole().getName())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't sign JWT object", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
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
}

