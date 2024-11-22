package com.backend.autocarrerbridge.dto.accountresponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePassWordDTO {
    String username;
    String password;
    String newPassword;
    String reNewPassword;
}
