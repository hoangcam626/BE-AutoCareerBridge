package com.backend.autocarrerbridge.emailconfig;

import lombok.Data;

import java.security.SecureRandom;

@Data
public class RandomCodeGenerator {

    private static final String CHARACTERS = "qwertyuiopasdfghjklzxcvbnmABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    private RandomCodeGenerator() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static String generateRegistrationCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }
}
