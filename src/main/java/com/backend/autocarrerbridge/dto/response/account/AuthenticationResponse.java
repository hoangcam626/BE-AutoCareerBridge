package com.backend.autocarrerbridge.dto.response.account;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String accessToken;

    @JsonIgnore
    String refreshToken;
}
