package com.backend.autocarrerbridge.util;

import com.backend.autocarrerbridge.exception.AppException;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EXPIRED_DATE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EXPIRED_DATE_FUTRURE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_SALARY;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_TITLE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_TITLE_OUT;


public class Validation {
    // Pre-compiled pattern for validating emails
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$"
    );
    private static final String PHONE_REGEX = "^\\d{10}$"; // Số điện thoại là 10 chữ số
    private static final String TAX_CODE_REGEX = "^\\d{10}$"; // Mã số thuế là 10 chữ số
    private static final String NUMBER_REGEX = "\\d+"; // Kiểm tra chỉ là số

    // Kiểm tra xem chuỗi có phải là email hợp lệ không
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Pattern.matches(PHONE_REGEX, phoneNumber);
    }

    public static boolean isValidTaxCode(String taxCode) {
        return taxCode != null && Pattern.matches(TAX_CODE_REGEX, taxCode);
    }

    public static void validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new AppException(ERROR_TITLE);
        }
        if (title.length() > 250) {
            throw new AppException(ERROR_TITLE_OUT);
        }
    }

    // Kiểm tra salary
    public static void validateSalary(Integer salary) {
        if (salary == null || salary == 0) {
            throw new AppException(ERROR_SALARY); // Trường hợp giá trị salary không có hoặc bằng 0
        }
        // Kiểm tra nếu salary là một số âm
        if (salary < 0) {
            throw new AppException(ERROR_SALARY); // Trường hợp salary nhỏ hơn 0
        }
    }

    // Kiểm tra expireDate
    public static void validateExpireDate(LocalDate expireDate) {
        if (expireDate == null) {
            throw new AppException(ERROR_EXPIRED_DATE);
        }
        // Kiểm tra nếu expireDate nhỏ hơn ngày hiện tại
        if (expireDate.isBefore(LocalDate.now())) {
            throw new AppException(ERROR_EXPIRED_DATE_FUTRURE);
        }
    }
}
