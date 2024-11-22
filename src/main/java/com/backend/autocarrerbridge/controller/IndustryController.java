package com.backend.autocarrerbridge.controller;


import com.backend.autocarrerbridge.dto.request.industry.IndustryRequest;
import com.backend.autocarrerbridge.dto.response.industry.IndustryResponse;
import com.backend.autocarrerbridge.dto.request.industry.IndustryUpdateRequest;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.IndustryService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/industry")
@Setter
@Getter
@RequiredArgsConstructor
@CrossOrigin("*")
public class IndustryController {
    private final IndustryService industryService;

    @GetMapping("/get-all-paging")
    public ApiResponse<Object> getAllIndustryPaging(
            @RequestParam(defaultValue = "0") int first,
            @RequestParam(defaultValue = "10") int rows,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {
        return industryService.getAllIndustryPaging(first, rows, page, name, code);
    }

    @GetMapping("/get-all")
    public ApiResponse<Object> getAllIndustry() {
        return industryService.getAllIndustry();
    }

    @PostMapping("/create")
    public ApiResponse<IndustryResponse> createIndustry(@RequestBody IndustryRequest industryRequest) {
        return industryService.createIndustry(industryRequest);
    }

    @PutMapping("/update")
    public ApiResponse<IndustryResponse> updateIndustry(@RequestBody IndustryUpdateRequest industryUpdateRequest) {
        return industryService.updateIndustry(industryUpdateRequest);
    }

    @PutMapping("/inactive")
    public ApiResponse<Object> inactiveIndustry(@RequestParam Integer id) {
        return industryService.inactiveIndustry(id);
    }
}
