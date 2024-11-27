package com.backend.autocarrerbridge.dto.response.province;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ProvinceResponse {
    private Integer id;
    private String name;
    private String fullName;
}
