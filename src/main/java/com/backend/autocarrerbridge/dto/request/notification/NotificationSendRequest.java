package com.backend.autocarrerbridge.dto.request.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class NotificationSendRequest {
    @NotNull
    private String toUsername;
    @NotBlank
    private String message;
}
