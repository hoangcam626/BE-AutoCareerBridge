package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminCreateRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminDeleteRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminSelfRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminUpdateRequest;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminCreateResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminDeleteResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminSelfResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminUpdateResponse;

public interface SubAdminService {
    SubAdminCreateResponse create(SubAdminCreateRequest req) throws ParseException;

    SubAdminUpdateResponse update(SubAdminUpdateRequest req) throws ParseException;

    SubAdminSelfResponse detail(SubAdminSelfRequest req);

    SubAdminDeleteResponse delete(SubAdminDeleteRequest req);

    Page<SubAdminSelfResponse> pageSubAdmins(int page, int pageSize);

    List<SubAdminSelfResponse> listSubAdmins();
}
