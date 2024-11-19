package com.backend.autocarrerbridge.dto;

import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBussinessDTO {
    private String username;
    private String name;
    private String taxCode;
    private String email;
    private String password;
    private String rePassword;
    private MultipartFile licenseImage;
    private Integer idRole;
}
