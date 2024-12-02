package com.backend.autocarrerbridge.dto.request.workshop;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class WorkshopApprovedRequest {
    @NotBlank(message = "NO_CONTENT_MESSAGE")
    private Integer id;
}
