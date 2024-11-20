package com.backend.autocarrerbridge.dto.subadmin.sdo;

import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SubAdminSelfSdo {

    private Integer id;

    private String subAdminCode;

    private String name;

    private String gender;

    private String email;

    private String phone;

    private String address;

    private MultipartFile subAdminImage;

    private Status status;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String createBy;

    private String updateBy;
}
