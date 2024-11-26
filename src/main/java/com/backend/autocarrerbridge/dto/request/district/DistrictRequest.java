package com.backend.autocarrerbridge.dto.request.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class DistrictRequest {
    private Integer provinceId;
}
