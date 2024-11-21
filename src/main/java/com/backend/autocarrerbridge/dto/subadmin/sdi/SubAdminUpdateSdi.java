package com.backend.autocarrerbridge.dto.subadmin.sdi;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminUpdateSdi {

    private Integer id;

    private String phone;

    private String address;

    private MultipartFile subAdminImage;
}
