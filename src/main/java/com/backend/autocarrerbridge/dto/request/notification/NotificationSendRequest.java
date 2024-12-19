package com.backend.autocarrerbridge.dto.request.notification;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class NotificationSendRequest {
    @NotNull(message = NO_CONTENT_MESSAGE)
    private List<String> toUsernames;

    @NotBlank(message = NO_CONTENT_MESSAGE)
    private String title;

    @NotBlank(message = NO_CONTENT_MESSAGE)
    private String message;
}
