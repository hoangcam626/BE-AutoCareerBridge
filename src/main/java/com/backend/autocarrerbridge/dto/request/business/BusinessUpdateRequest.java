package com.backend.autocarrerbridge.dto.request.business;

import com.backend.autocarrerbridge.dto.request.location.LocationRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BusinessUpdateRequest {

    private String name;

    private String taxCode;

    private String companySize;

    private String website;

    private Integer foundYear;

    private String email;

    private String phone;

    private String description;

    private Integer businessImageId;

    private Integer licenseImageId;

    private LocationRequest locationRequest;

}
