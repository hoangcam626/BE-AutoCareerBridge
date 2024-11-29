package com.backend.autocarrerbridge.dto.request.province;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProvinceRequest {
    @NotBlank(message = "ERROR_PROVINCE_NOT_BLANK")
    private Integer id;
}
