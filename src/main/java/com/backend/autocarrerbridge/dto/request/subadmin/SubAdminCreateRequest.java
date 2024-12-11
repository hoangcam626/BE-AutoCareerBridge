package com.backend.autocarrerbridge.dto.request.subadmin;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NO_CODE_SUB_ADMIN;

import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminCreateRequest {

    @NotBlank(message = NO_CODE_SUB_ADMIN)
    private String subAdminCode;

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    private String name;

    private String gender;

    @NotBlank(message = EMAIL_REQUIRED_MESSAGE)
    private String email;

    private String phone;

    private String address;

    private MultipartFile subAdminImage;
}
