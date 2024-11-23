package com.backend.autocarrerbridge.dto.response.university;

import com.backend.autocarrerbridge.dto.request.account.RoleRequest;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityRegisterResponse {
    String username;
    String name;
    String email;
    String phone;
    RoleRequest role;
    State state;
}
