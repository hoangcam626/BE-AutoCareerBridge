package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import com.backend.autocarrerbridge.service.NotificationService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import com.backend.autocarrerbridge.entity.Notification;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.entity.UserNotification;
import com.backend.autocarrerbridge.repository.NotificationRepository;
import com.backend.autocarrerbridge.repository.UserNotificationRepository;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserAccountService userAccountService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @Override
    public NotificationResponse send(NotificationSendRequest req) throws ParseException {

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
        NotificationResponse res = modelMapper.map(notification, NotificationResponse.class);
        pushNotificationToUser(userAccount.getUsername(), res);
        return res;
    }

    @Override
    public List<NotificationResponse> getAllUserNotification() throws ParseException {
        String usernameLogin = tokenService.getClaim(tokenService.getJWT(), SUB);
        UserAccount userAccount = userAccountService.getUserByUsername(usernameLogin);
        List<Notification> notifications =
                userNotificationRepository.getAllNotificationByUserAccountId(userAccount.getId());
        return notifications.stream()
                .map(n -> modelMapper.map(n, NotificationResponse.class))
                .toList();
    }

    @Override
    public SseEmitter createConnection() throws ParseException {
        String username = tokenService.getClaim(tokenService.getJWT(), SUB);
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitters.put(username, emitter);
        emitter.onCompletion(() -> sseEmitters.remove(username));
        emitter.onTimeout(() -> sseEmitters.remove(username));
        return emitter;
    }

    private void pushNotificationToUser(String username, NotificationResponse notification) {
        SseEmitter emitter = sseEmitters.get(username);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter
                        .event()
                        .name("notification")
                        .data(notification));
            } catch (IOException e) {
                // Xóa kết nối nếu không còn hợp lệ
                sseEmitters.remove(username);
            }
        }
    }
}
