package com.backend.autocarrerbridge.dto.response.notification;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.entity.UserNotification;
import com.backend.autocarrerbridge.util.enums.StatusRead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class NotificationResponse {
    private Integer id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private String createdBy;
}
