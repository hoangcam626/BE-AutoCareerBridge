package com.backend.autocarrerbridge.dto.response.account;

import com.backend.autocarrerbridge.dto.response.RoleResponse;

import lombok.*;
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
