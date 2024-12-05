package com.backend.autocarrerbridge.service;

import java.text.ParseException;
import java.util.List;

import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.notification.NotificationDetailResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationSendResponse;

public interface NotificationService {
    NotificationSendResponse send(NotificationSendRequest req) throws ParseException;

    List<NotificationDetailResponse> getAllUserNotification() throws ParseException;
}
