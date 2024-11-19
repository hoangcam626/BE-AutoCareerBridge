package com.backend.autocarrerbridge.dto;

import com.backend.autocarrerbridge.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUniversityDTO {
    private String name;
    private String email;
    private String phone;

    private String password;

    private String rePassword;

    private Integer idRole;
}
