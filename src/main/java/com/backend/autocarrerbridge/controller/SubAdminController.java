package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.subadmin.sdi.*;
import com.backend.autocarrerbridge.dto.subadmin.sdo.*;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.SubAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SCOPE_Admin')")
@RequestMapping("/api/sub-admin")
public class SubAdminController {
    private final SubAdminService subAdminService;
    @PostMapping("/create")
    public ApiResponse<SubAdminCreateSdo> create(@ModelAttribute SubAdminCreateSdi req) throws ParseException {

        var res = subAdminService.create(req);
        return new ApiResponse<>(res);
    }
    @PutMapping("/update")
    public ApiResponse<SubAdminUpdateSdo> update(SubAdminUpdateSdi req) throws ParseException {
        var res = subAdminService.update(req);
        return new ApiResponse<>(res);
    }

    @GetMapping("/self")
    public ApiResponse<SubAdminSelfSdo> self(@RequestParam("id") Integer id){
        var res = subAdminService.self(SubAdminSelfSdi.of(id));
        return new ApiResponse<>(res);
    }
    @DeleteMapping("/delete")
    public ApiResponse<SubAdminDeleteSdo> delete(SubAdminDeleteSdi req) {
        var res = subAdminService.delete(req);
        return new ApiResponse<>(res);
    }

    @GetMapping("/list")
    public ApiResponse<List<SubAdminSelfSdo>> getSubAdmins(){
        var res = subAdminService.listSubAdmins();
        return new ApiResponse<>(res);
    }

    @GetMapping("/page")
    public ApiResponse<Page<SubAdminSelfSdo>> getSubAdmins(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        var res = subAdminService.pageSubAdmins(page, pageSize);
        return new ApiResponse<>(res);
    }
}