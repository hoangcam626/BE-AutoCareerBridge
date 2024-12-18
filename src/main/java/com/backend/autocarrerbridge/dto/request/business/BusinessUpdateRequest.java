package com.backend.autocarrerbridge.dto.request.business;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.backend.autocarrerbridge.util.Constant.EMAIL_INVALID_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.FOUNDED_YEAR_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PHONE_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TAX_CODE;
import static com.backend.autocarrerbridge.util.Constant.WEBSITE_UNIVERSITY_NOT_BLANK_MESSAGE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BusinessUpdateRequest {

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    private String name;

    @NotBlank(message = TAX_CODE)
    private String taxCode;

    private String companySize;

    @NotNull(message = WEBSITE_UNIVERSITY_NOT_BLANK_MESSAGE)
    private String website;

    @NotNull(message = FOUNDED_YEAR_UNIVERSITY_NOT_BLANK_MESSAGE)
    private Integer foundYear;

    @Pattern(regexp = "^\\+?[0-9]*$", message = PHONE_NOT_BLANK_MESSAGE)
    private String phone;

    private String description;

    private MultipartFile businessImage;

    private MultipartFile licenseImage;

    private String descriptionLocation;

    private Integer provinceId;

    private Integer districtId;

    private Integer wardId;
}
