package com.backend.autocarrerbridge.dto.response.notification;

import com.backend.autocarrerbridge.entity.UserNotification;
import com.backend.autocarrerbridge.util.enums.StatusRead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserNotificationResponse {
    private Integer id;
    private String title;
    private String message;
    private StatusRead statusRead;
    private LocalDateTime createdAt;
    private String createdBy;

    public UserNotificationResponse(UserNotification userNotification){
        this.id = userNotification.getId();
        this.title = userNotification.getNotification().getTitle();
        this.message = userNotification.getNotification().getMessage();
        this.createdBy = userNotification.getNotification().getCreatedBy();
        this.createdAt = userNotification.getCreatedAt();
        this.statusRead = userNotification.getStatusRead();
    }
}
