package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.statistic.AdminStatisticResponse;
import com.backend.autocarrerbridge.dto.response.statistic.DateStatisticResponse;
import com.backend.autocarrerbridge.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistic")
public class AdminStatisticController {
    private final AdminService adminService;

    @GetMapping("/get-total")
    public ApiResponse<AdminStatisticResponse> adminStatistic() {
        var res = adminService.total();
        return new ApiResponse<>(res);
    }

    @GetMapping("/get-date-total")
    public ApiResponse<Map<LocalDate, DateStatisticResponse>> dateStatistic(@RequestParam("startDate") LocalDate startDate,
                                                                            @RequestParam("endDate") LocalDate endDate) {
        var res = adminService.totalOnDate(startDate, endDate);
        return new ApiResponse<>(res);
    }
}
