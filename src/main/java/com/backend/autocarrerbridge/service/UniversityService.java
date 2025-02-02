package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.request.account.UserUniversityRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.request.university.UniversityApprovedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRejectedRequest;
import com.backend.autocarrerbridge.dto.request.university.UniversityRequest;
import com.backend.autocarrerbridge.dto.response.business.BusinessSearchPage;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.university.AdminUniversityResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityApprovedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityListHome;
import com.backend.autocarrerbridge.dto.response.university.UniversityRegisterResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityRejectedResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversitySearchPage;
import com.backend.autocarrerbridge.dto.response.university.UniversityTotalResponse;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.util.email.EmailCode;
import com.backend.autocarrerbridge.util.enums.State;
import org.springframework.data.domain.Pageable;

public interface UniversityService {
    UniversityRegisterResponse registerUniversity(UserUniversityRequest userUniversityRequest);

    UniversityApprovedResponse approvedAccount(UniversityApprovedRequest req);

    UniversityRejectedResponse rejectedAccount(UniversityRejectedRequest req);

    UniversityResponse update(int id, UniversityRequest universityRequest);

    List<UniversityResponse> getById(int id);

    List<UniversityResponse> getAll();

    University findById(Integer id);

    List<UniversityResponse> findUniversityByNameOrLocation(String address, String universityName);

    PagingResponse<AdminUniversityResponse> getPagingByState(PageInfo req, State state);

    PagingResponse<AdminUniversityResponse> getAllUniversities(PageInfo req);

    EmailCode generateCode(UserUniversityRequest userUniversityRequest);

    AdminUniversityResponse detail(Integer id);

    List<UniversityTotalResponse> getAllTotalUniversity();

    Long countUniversityTotal();

    List<UniversityListHome> getUniversityListHome();

    PagingResponse<UniversitySearchPage> getAllUniversityPage(String keyword, Pageable pageable);
}
