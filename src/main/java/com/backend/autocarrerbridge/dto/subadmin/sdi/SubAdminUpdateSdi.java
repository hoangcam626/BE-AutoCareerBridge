package com.backend.autocarrerbridge.dto.subadmin.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminUpdateSdi {

    private Integer id;

    private String phone;

    private String address;

    private MultipartFile subAdminImage;
}
