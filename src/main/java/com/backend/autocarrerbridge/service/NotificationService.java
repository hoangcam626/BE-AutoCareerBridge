package com.backend.autocarrerbridge.service;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.request.notification.UserNotificationMarkReadRequest;
import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.notification.UnReadAmountResponse;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationMarkReadResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {
    NotificationResponse send(NotificationSendRequest req) throws ParseException;

    SseEmitter createConnection(int userId);

    PagingResponse<UserNotificationResponse> getAllUserNotificationPaging(PageInfo req) throws ParseException;

    UserNotificationMarkReadResponse markReadNotification(UserNotificationMarkReadRequest req) throws ParseException;

    UserNotificationMarkReadResponse markReadAllNotification() throws ParseException;

    UnReadAmountResponse countUserNotificationUnread() throws ParseException;
}
