package com.backend.autocarrerbridge.util;

import java.util.regex.Pattern;

public class Validation {
    // Pre-compiled pattern for validating emails
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + // Local part
                    "(?:[a-zA-Z0-9-]+\\.)+"
                    + // Domain
                    "[a-zA-Z]{2,}$" // Top-level domain
            );

    // Kiểm tra xem chuỗi có phải là email hợp lệ không
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}
