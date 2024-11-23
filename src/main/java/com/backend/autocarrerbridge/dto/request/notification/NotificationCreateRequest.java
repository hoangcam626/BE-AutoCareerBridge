package com.backend.autocarrerbridge.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class NotificationCreateRequest {
    private String message;
}
