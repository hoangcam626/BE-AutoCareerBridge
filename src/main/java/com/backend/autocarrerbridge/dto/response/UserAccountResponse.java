package com.backend.autocarrerbridge.dto.response;

import com.backend.autocarrerbridge.entity.Role;
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

    Role role;
}
