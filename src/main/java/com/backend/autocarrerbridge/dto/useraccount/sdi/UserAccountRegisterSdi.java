package com.backend.autocarrerbridge.dto.useraccount.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class UserAccountRegisterSdi {
    private String username;
    private String password;
    private String nameRole;
}
