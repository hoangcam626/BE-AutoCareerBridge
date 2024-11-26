package com.backend.autocarrerbridge.dto.request.ward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class WardRequest {
    private Integer provinceId;
    private Integer districtId;
}
