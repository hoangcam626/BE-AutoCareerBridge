package com.backend.autocarrerbridge.util;

import java.util.regex.Pattern;

public class Validation {
    // Pre-compiled pattern for validating emails
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$"
    );
    private static final String PHONE_REGEX = "^\\d{10}$"; // Số điện thoại là 10 chữ số
    private static final String TAX_CODE_REGEX = "^\\d{10}$"; // Mã số thuế là 10 chữ số
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";
    // Kiểm tra xem chuỗi có phải là email hợp lệ không
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches(PHONE_REGEX, phoneNumber);
    }
    public static boolean isValidPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_PATTERN, password);
    }

    public static boolean isValidTaxCode(String taxCode) {
        return taxCode != null && Pattern.matches(TAX_CODE_REGEX, taxCode);
    }
}
