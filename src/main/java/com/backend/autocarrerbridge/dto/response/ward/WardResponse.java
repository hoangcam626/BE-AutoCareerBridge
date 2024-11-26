package com.backend.autocarrerbridge.dto.response.ward;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class WardResponse {
    private Integer id;
    private String name;
    private String fullName;
}
