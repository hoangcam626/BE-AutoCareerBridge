package com.backend.autocarrerbridge.dto.response.notification;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NotificationDetailResponse {
    private String message;
    private LocalDateTime createdAt;
    private String createdBy;
}
