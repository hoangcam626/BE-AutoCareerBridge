package com.backend.autocarrerbridge.dto.AccountRespone;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBusinessDTO {

    String name;

    String taxCode;

    String email;

    String password;

    String rePassword;

    MultipartFile licenseImage;

    String verificationCode;
}
