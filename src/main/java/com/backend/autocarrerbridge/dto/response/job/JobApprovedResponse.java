package com.backend.autocarrerbridge.dto.response.job;

import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class JobApprovedResponse {
    private Boolean success;
    private NotificationResponse notification;
}
