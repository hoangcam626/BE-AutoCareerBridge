package com.backend.autocarrerbridge.dto.request;

import com.backend.autocarrerbridge.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountRequest {
    String username;

    String password;

    Role role;
}
