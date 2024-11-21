package com.backend.autocarrerbridge.emailconfig;

import java.security.SecureRandom;

public class RandomCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8; // Độ dài mã
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?/";

    public static String generateRegistrationCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        // Đảm bảo có ít nhất 1 ký tự in hoa, 1 chữ số và 1 ký tự đặc biệt
        code.append(UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length())));
        code.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        code.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        // Chèn thêm ký tự ngẫu nhiên cho đủ độ dài
        while (code.length() < CODE_LENGTH) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        // Trộn các ký tự lại để đảm bảo phân phối ngẫu nhiên
        String result = code.toString();
        StringBuilder shuffledCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < result.length(); i++) {
            int randomIndex = random.nextInt(result.length());
            shuffledCode.append(result.charAt(randomIndex));
        }

        return shuffledCode.toString();
    }
}
