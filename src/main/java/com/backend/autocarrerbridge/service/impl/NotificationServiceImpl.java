package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.service.NotificationService;
import java.text.ParseException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.notification.NotificationDetailResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationSendResponse;
import com.backend.autocarrerbridge.entity.Notification;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.entity.UserNotification;
import com.backend.autocarrerbridge.repository.NotificationRepository;
import com.backend.autocarrerbridge.repository.UserNotificationRepository;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserAccountService userAccountService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    @Override
    public NotificationSendResponse send(NotificationSendRequest req) throws ParseException {

        // Create
        Notification notification =
                Notification.builder().message(req.getMessage()).statusRead(0).build();
        String usernameLogin = tokenService.getClaim(tokenService.getJWT(), SUB);
        notification.setCreatedBy(usernameLogin);
        notification = notificationRepository.save(notification);

        // Send
        UserAccount userAccount = userAccountService.getUserByUsername(req.getToUsername());
        UserNotification userNotification = UserNotification.builder()
                .userAccount(userAccount)
                .notification(notification)
                .build();
        userNotificationRepository.save(userNotification);
        return NotificationSendResponse.of(notification.getId());
    }

    @Override
    public List<NotificationDetailResponse> getAllUserNotification() throws ParseException {
        String usernameLogin = tokenService.getClaim(tokenService.getJWT(), SUB);
        UserAccount userAccount = userAccountService.getUserByUsername(usernameLogin);
        List<Notification> notifications =
                userNotificationRepository.getAllNotificationByUserAccountId(userAccount.getId());
        return notifications.stream()
                .map(n -> modelMapper.map(n, NotificationDetailResponse.class))
                .toList();
    }
}
