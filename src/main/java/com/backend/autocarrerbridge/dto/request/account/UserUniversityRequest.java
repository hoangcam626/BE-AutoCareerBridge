package com.backend.autocarrerbridge.dto.request.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUniversityRequest {

    String name;

    String email;

    String phone;

    String password;

    String rePassword;

    String verificationCode;
}
