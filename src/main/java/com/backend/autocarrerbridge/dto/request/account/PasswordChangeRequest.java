package com.backend.autocarrerbridge.dto.request.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordChangeRequest {
    String username;
    String password;
    String newPassword;
    String reNewPassword;
}
