package com.backend.autocarrerbridge.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.backend.autocarrerbridge.dto.response.cooperation.SentRequestResponse;
import com.backend.autocarrerbridge.entity.BusinessUniversity;

@Component
public class SentRequestConverter {

    /**
     * Chuyển đổi đối tượng BusinessUniversity thành SentRequestResponse
     */
    public List<SentRequestResponse> toSentRequestResponse(List<BusinessUniversity> businessUniversity) {
        List<SentRequestResponse> sentRequestResponses = new ArrayList<>();
        if (businessUniversity != null) {
            for (BusinessUniversity businessUniversities : businessUniversity) {
                SentRequestResponse sentRequestResponse = new SentRequestResponse(businessUniversities);
                sentRequestResponses.add(sentRequestResponse);
            }
        }
        return sentRequestResponses;
    }
}
