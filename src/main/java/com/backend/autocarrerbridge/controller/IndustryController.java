package com.backend.autocarrerbridge.controller;

import org.springframework.web.bind.annotation.*;

import com.backend.autocarrerbridge.dto.industry.IndustrySdi;
import com.backend.autocarrerbridge.dto.industry.IndustrySdo;
import com.backend.autocarrerbridge.dto.industry.IndustryUpdateSdi;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.IndustryService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    public ApiResponse<IndustrySdo> createIndustry(@RequestBody IndustrySdi industrySdi) {
        return industryService.createIndustry(industrySdi);
    }

    @PutMapping("/update")
    public ApiResponse<IndustrySdo> updateIndustry(@RequestBody IndustryUpdateSdi industryUpdateSdi) {
        return industryService.updateIndustry(industryUpdateSdi);
    }

    @PutMapping("/inactive")
    public ApiResponse<Object> inactiveIndustry(@RequestParam Integer id) {
        return industryService.inactiveIndustry(id);
    }
}
