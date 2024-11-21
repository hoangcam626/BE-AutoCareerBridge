package com.backend.autocarrerbridge.emailconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String email;
    private String subject;
    private String body;
}
