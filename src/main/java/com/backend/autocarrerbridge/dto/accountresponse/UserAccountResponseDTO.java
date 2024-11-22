package com.backend.autocarrerbridge.dto.accountresponse;

import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountResponseDTO {

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
