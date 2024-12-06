package com.backend.autocarrerbridge.dto.request.subadmin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

import static com.backend.autocarrerbridge.util.Constant.NAME_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminUpdateRequest {

    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer id;

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    private String name;

    private String gender;

    private String phone;

    private String address;

    private MultipartFile subAdminImage;
}
