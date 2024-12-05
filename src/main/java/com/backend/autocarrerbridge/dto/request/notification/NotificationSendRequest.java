package com.backend.autocarrerbridge.dto.request.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

@Data
@AllArgsConstructor(staticName = "of")
public class NotificationSendRequest {

    @NotBlank(message = NO_CONTENT_MESSAGE)
    private String toUsername;

    @NotBlank(message = NO_CONTENT_MESSAGE)
    private String message;
}
