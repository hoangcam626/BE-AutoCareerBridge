package com.backend.autocarrerbridge.emailconfig;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmailCodeRequest {
    @NotBlank(message = EMAIL_REQUIRED_MESSAGE)
    private String email;
}
