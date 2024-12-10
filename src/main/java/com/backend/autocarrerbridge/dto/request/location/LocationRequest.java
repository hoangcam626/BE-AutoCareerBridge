package com.backend.autocarrerbridge.dto.request.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LocationRequest{
    private Integer id;

    private String description;

    private Integer provinceId;

    private Integer districtId;

    private Integer wardId;
}
