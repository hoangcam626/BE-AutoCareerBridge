package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.request.notification.UserNotificationMaskReadRequest;
import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationMaskReadResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    NotificationResponse send(NotificationSendRequest req) throws ParseException;

    SseEmitter createConnection(int userId);

    PagingResponse<UserNotificationResponse> getAllUserNotificationPaging(PageInfo req) throws ParseException;

    UserNotificationMaskReadResponse maskReadNotification(UserNotificationMaskReadRequest req) throws ParseException;

    UserNotificationMaskReadResponse maskReadAllNotification() throws ParseException;
}
