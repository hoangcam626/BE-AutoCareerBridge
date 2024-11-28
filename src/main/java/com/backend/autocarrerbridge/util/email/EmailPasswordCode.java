package com.backend.autocarrerbridge.util.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailPasswordCode {
    private String email;
    private String password;
}
