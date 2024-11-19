package com.backend.autocarrerbridge.dto.AccountRespone;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBusinessDTO {
     String username;
     String name;
     String taxCode;
     String email;
     String password;
     String rePassword;
     MultipartFile licenseImage;
}
