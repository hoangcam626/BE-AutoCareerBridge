package com.backend.autocarrerbridge.dto.AccountRespone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUniversityDTO {

     String name;

     String email;

     String phone;

     String password;

     String rePassword;

     String verificationCode;
}
