package com.backend.autocarrerbridge.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import com.backend.autocarrerbridge.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class  NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/stream")
    public SseEmitter stream() throws ParseException {
        return notificationService.createConnection();
    }


    @GetMapping("/get-all")
    public ApiResponse<List<NotificationResponse>> getAllUserNotification() throws ParseException {
        var res = notificationService.getAllUserNotification();
        return new ApiResponse<>(res);
    }
}
