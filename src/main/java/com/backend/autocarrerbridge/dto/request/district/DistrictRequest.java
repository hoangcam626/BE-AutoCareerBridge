package com.backend.autocarrerbridge.dto.request.district;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class DistrictRequest {
    @NotBlank(message = "ERROR_DISTRICT_NOT_BLANK")
    private Integer id;
}
