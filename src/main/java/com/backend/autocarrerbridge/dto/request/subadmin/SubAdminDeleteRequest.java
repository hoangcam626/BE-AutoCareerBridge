package com.backend.autocarrerbridge.dto.request.subadmin;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class SubAdminDeleteRequest {
    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer id;
}
