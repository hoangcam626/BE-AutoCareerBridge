package com.backend.autocarrerbridge.dto.request.job;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class JobApprovedRequest {
    @NotBlank(message = "NO_CONTENT_MESSAGE")
    private Integer id;
}
