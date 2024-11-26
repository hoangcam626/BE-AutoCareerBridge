package com.backend.autocarrerbridge.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminCreateRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminDeleteRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminSelfRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminUpdateRequest;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminCreateResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminDeleteResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminSelfResponse;
import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminUpdateResponse;
import com.backend.autocarrerbridge.service.SubAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_Admin')")
@RequestMapping("/api/sub-admin")
public class SubAdminController {
    private final SubAdminService subAdminService;

    @PostMapping("/create")
    public ApiResponse<SubAdminCreateResponse> create(@ModelAttribute SubAdminCreateRequest req) throws ParseException {

        var res = subAdminService.create(req);
        return new ApiResponse<>(res);
    }

    @PutMapping("/update")
    public ApiResponse<SubAdminUpdateResponse> update(SubAdminUpdateRequest req) throws ParseException {
        var res = subAdminService.update(req);
        return new ApiResponse<>(res);
    }

    @GetMapping("/self")
    public ApiResponse<SubAdminSelfResponse> self(@RequestParam("id") Integer id) {
        var res = subAdminService.self(SubAdminSelfRequest.of(id));
        return new ApiResponse<>(res);
    }

    @DeleteMapping("/delete")
    public ApiResponse<SubAdminDeleteResponse> delete(SubAdminDeleteRequest req) {
        var res = subAdminService.delete(req);
        return new ApiResponse<>(res);
    }

    @GetMapping("/list")
    public ApiResponse<List<SubAdminSelfResponse>> getSubAdmins() {
        var res = subAdminService.listSubAdmins();
        return new ApiResponse<>(res);
    }

    @GetMapping("/page")
    public ApiResponse<Page<SubAdminSelfResponse>> getSubAdmins(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        var res = subAdminService.pageSubAdmins(page, pageSize);
        return new ApiResponse<>(res);
    }
}
