package com.backend.autocarrerbridge.dto.request.location;

import com.backend.autocarrerbridge.entity.AbstractAudit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LocationRequest extends AbstractAudit {
    private Integer id;

    private String description;

    private Integer provinceId;

    private Integer districtId;

    private Integer wardId;
}
