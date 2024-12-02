package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationDetailResponse;
import com.backend.autocarrerbridge.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

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
