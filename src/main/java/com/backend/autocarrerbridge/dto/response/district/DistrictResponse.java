package com.backend.autocarrerbridge.dto.response.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class DistrictResponse {
    private Integer id;
    private String name;
    private String fullName;
}
