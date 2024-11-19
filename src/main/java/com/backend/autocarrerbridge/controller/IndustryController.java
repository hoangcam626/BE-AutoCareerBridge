package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.industry.IndustrySdi;
import com.backend.autocarrerbridge.dto.industry.IndustrySdo;
import com.backend.autocarrerbridge.dto.industry.IndustryUpdateSdi;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.IndustryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/industry")
@Setter
@Getter
@RequiredArgsConstructor
public class IndustryController {
    private final IndustryService industryService;

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
