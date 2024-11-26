package com.backend.autocarrerbridge.emailconfig;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    @NotBlank(message = EMAIL_REQUIRED_MESSAGE)
    @NotNull(message = EMAIL_REQUIRED_MESSAGE)
    private String email;

    private String subject;
    private String body;
}
