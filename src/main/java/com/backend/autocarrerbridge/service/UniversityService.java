package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityApprovedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRejectedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.entity.University;
import org.springframework.data.domain.Page;

public interface UniversityService {
    UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest);

    UniversityApprovedResponse approvedAccount(UniversityApprovedRequest req);

    UniversityRejectedResponse rejectedAccount(UniversityRejectedRequest req);

    UniversityResponse update(int id, UniversityRequest universityRequest);

    List<UniversityResponse> getById(int id);

    List<UniversityResponse> getAll();

    University findById(Integer id);

    List<UniversityResponse> findUniversityByNameOrLocation(String address, String universityName);

    Page<UniversityResponse> getPagingByState(PageInfo req, Integer state);
}
