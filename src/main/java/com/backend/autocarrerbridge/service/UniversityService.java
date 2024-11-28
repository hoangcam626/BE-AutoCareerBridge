package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.entity.University;

public interface UniversityService {
    UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest);
    void approvedAccount(UniversityApprovedRequest req);
    void rejectedAccount(UniversityRejectedRequest req);

    University findById(Integer id);
}
