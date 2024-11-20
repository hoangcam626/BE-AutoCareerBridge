package com.backend.autocarrerbridge.dto.subadmin.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminCreateSdi {

    private String subAdminCode;

    private String name;

    private String gender;

    private String email;

    private String phone;

    private String address;

    private String password;

    private String rePassword;

    private MultipartFile subAdminImage;
}
