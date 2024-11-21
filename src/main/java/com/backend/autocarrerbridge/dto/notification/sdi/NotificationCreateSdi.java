package com.backend.autocarrerbridge.dto.notification.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class NotificationCreateSdi {
    private String message;
}
