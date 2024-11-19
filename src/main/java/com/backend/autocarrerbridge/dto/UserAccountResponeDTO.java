package com.backend.autocarrerbridge.dto;



import com.backend.autocarrerbridge.entity.Role;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountResponeDTO {
    Integer id;
    String username;
    String password;
    Status status;
    @JsonIgnore
    LocalDateTime createdAt;
    @JsonIgnore
    LocalDateTime updatedAt;
    @JsonIgnore
    Integer createdBy;
    @JsonIgnore
    Integer updatedBy;
    RoleDTO role;
    AuthenticationResponse authenticationResponse;

}
