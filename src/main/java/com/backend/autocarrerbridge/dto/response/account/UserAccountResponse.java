package com.backend.autocarrerbridge.dto.response.account;

import com.backend.autocarrerbridge.dto.response.RoleResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountResponse {
    Integer id;

    String username;

    String password;

    RoleResponse role;
}
