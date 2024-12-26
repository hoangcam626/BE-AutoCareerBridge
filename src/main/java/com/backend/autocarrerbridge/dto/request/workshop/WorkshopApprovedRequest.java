package com.backend.autocarrerbridge.dto.request.workshop;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class WorkshopApprovedRequest {
    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer id;
}
