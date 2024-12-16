package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    NotificationResponse send(NotificationSendRequest req) throws ParseException;

    List<NotificationResponse> getAllUserNotification() throws ParseException;

    SseEmitter createConnection() throws ParseException;
}
