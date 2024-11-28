package com.backend.autocarrerbridge.dto.request.subadmin;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminCreateRequest {

    private String subAdminCode;

    private String name;

    private String gender;

    private String email;

    private String phone;

    private String address;

    private MultipartFile subAdminImage;
}
