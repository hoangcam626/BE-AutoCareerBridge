package com.backend.autocarrerbridge.dto.request.account;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.dto.response.account.AuthenticationResponse;
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
public class UserAccountRequest {

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

    RoleRequest role;

    AuthenticationResponse authenticationResponse;
}
