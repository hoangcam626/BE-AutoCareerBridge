package com.backend.autocarrerbridge.dto.request.employee;

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
}
