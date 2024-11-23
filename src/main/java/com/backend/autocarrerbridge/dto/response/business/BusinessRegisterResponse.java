package com.backend.autocarrerbridge.dto.response.business;

import com.backend.autocarrerbridge.dto.request.account.RoleRequest;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessRegisterResponse {
    String name;
    String taxCode;
    String email;
    RoleRequest role;
    State state;
}
