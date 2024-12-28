package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.response.statistic.AdminStatisticResponse;
import com.backend.autocarrerbridge.dto.response.statistic.DateStatisticResponse;

import java.time.LocalDate;
import java.util.Map;

public interface AdminService {
    AdminStatisticResponse total();
    Map<LocalDate, DateStatisticResponse> totalOnDate(LocalDate startDate, LocalDate endDate);
}
