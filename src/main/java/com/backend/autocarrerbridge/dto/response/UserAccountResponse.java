package com.backend.autocarrerbridge.dto.response;

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
