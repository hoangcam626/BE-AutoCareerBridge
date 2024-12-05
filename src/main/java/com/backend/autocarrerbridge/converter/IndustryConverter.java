package com.backend.autocarrerbridge.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.autocarrerbridge.dto.response.industry.BusinessIndustryDto;
import com.backend.autocarrerbridge.entity.BusinessIndustry;

@Component
public class IndustryConverter {
    /**
     * Chuyển đổi đối tượng BusinessUniversity thành SentRequestResponse
     */
    public List<BusinessIndustryDto> toBusinessIndustryResponse(List<BusinessIndustry> businessIndustryList) {
        List<BusinessIndustryDto> industryResponses = new ArrayList<>();
        if (businessIndustryList != null) {
            for (BusinessIndustry businessUniversity : businessIndustryList) {
                BusinessIndustryDto industryResponse = new BusinessIndustryDto(businessUniversity);
                industryResponses.add(industryResponse);
            }
        }
        return industryResponses;
    }
}
