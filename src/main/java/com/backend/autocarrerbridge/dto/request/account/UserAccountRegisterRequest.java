package com.backend.autocarrerbridge.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class UserAccountRegisterRequest {
    private String username;
    private String password;
    private String nameRole;
}
