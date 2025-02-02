package com.backend.autocarrerbridge.dto.response.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserNotificationMarkReadResponse {
    private Integer id;
    private Boolean success;
}
