package com.backend.autocarrerbridge.dto.response.cooperation;

import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CooperationRejectResponse {
    private Boolean success;
    private NotificationResponse notification;
}
