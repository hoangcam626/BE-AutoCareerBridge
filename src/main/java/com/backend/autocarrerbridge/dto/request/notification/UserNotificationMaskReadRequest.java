package com.backend.autocarrerbridge.dto.request.notification;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserNotificationMaskReadRequest {
    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer id;
}
