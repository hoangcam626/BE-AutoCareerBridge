package com.backend.autocarrerbridge.dto.response.account;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.dto.request.account.RoleRequest;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccountLoginResponse {
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

    RoleRequest role;
    String accessToken;
}
