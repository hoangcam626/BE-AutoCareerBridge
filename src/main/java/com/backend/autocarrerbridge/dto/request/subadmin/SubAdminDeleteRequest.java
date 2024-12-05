package com.backend.autocarrerbridge.dto.request.subadmin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminDeleteRequest {
    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer id;
}
