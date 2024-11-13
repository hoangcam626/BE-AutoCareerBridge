package com.backend.autocarrerbridge.util;

import java.util.regex.Pattern;

public class Validation {
  // Kiểm tra xem chuỗi có phải là email hợp lệ không
  public static boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    return email != null && Pattern.matches(emailRegex, email);
  }

  // Kiểm tra xem chuỗi có phải là số điện thoại hợp lệ không
  public static boolean isValidPhoneNumber(String phoneNumber) {
    String phoneRegex = "^\\d{10}$"; // Giả sử số điện thoại là 10 chữ số
    return phoneNumber != null && Pattern.matches(phoneRegex, phoneNumber);
  }

  // Kiểm tra xem chuỗi không được null và không rỗng
  public static boolean isNotNullOrEmpty(String str) {
    return str != null && !str.trim().isEmpty();
  }

  // Kiểm tra độ dài của một chuỗi có nằm trong khoảng hợp lệ không
  public static boolean isValidLength(String str, int min, int max) {
    return str != null && str.length() >= min && str.length() <= max;
  }

}
