package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.notification.NotificationDetailResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationSendResponse;

import java.text.ParseException;
import java.util.List;

public interface NotificationService {
    NotificationSendResponse send(NotificationSendRequest req) throws ParseException;
    List<NotificationDetailResponse> getAllUserNotification() throws ParseException;
}
