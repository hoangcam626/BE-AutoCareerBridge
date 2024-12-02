package com.backend.autocarrerbridge.dto.response.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NotificationDetailResponse {
    private String message;
    private LocalDateTime createdAt;
    private String createdBy;
}
