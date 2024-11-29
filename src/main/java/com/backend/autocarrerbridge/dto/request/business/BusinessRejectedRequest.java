package com.backend.autocarrerbridge.dto.request.business;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class BusinessRejectedRequest {
    @NotBlank(message = "NO_CONTENT_MESSAGE")
    private Integer id;
}
