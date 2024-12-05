package com.backend.autocarrerbridge.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationDetailResponse;
import com.backend.autocarrerbridge.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/get-all")
    public ApiResponse<List<NotificationDetailResponse>> getAllUserNotification() throws ParseException {
        var res = notificationService.getAllUserNotification();
        return new ApiResponse<>(res);
    }
}
