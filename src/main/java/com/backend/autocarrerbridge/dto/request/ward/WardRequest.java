package com.backend.autocarrerbridge.dto.request.ward;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class WardRequest {
    @NotBlank(message = "ERROR_WARD_NOT_BLANK")
    private Integer id;
}
