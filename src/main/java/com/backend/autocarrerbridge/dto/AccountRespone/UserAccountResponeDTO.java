package com.backend.autocarrerbridge.dto.AccountRespone;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountResponeDTO {

    Integer id;

    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự")
    String username;

    @NotBlank(message = "Password không được để trống")
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
