package com.backend.autocarrerbridge.dto;

import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayUserAccountDTO {
    Integer id;
    String username;
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
