package com.backend.autocarrerbridge.dto.request.business;

import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile businessImage;

    private MultipartFile licenseImage;

    private String descriptionLocation;

    private Integer provinceId;

    private Integer districtId;

    private Integer wardId;
}
