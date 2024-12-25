package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NOTIFICATION_ALREADY_READ;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NOTIFICATION_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.SUB;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.backend.autocarrerbridge.dto.response.notification.UnReadAmountResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.backend.autocarrerbridge.dto.request.notification.NotificationSendRequest;
import com.backend.autocarrerbridge.dto.request.notification.UserNotificationMarkReadRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationMarkReadResponse;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.dto.response.notification.NotificationResponse;
import com.backend.autocarrerbridge.entity.Notification;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.entity.UserNotification;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.NotificationRepository;
import com.backend.autocarrerbridge.repository.UserNotificationRepository;
import com.backend.autocarrerbridge.service.NotificationService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.enums.StatusRead;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserAccountService userAccountService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;

    private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    /**
     * Gửi thông báo đén người dùng mong muốn
     *
     * @param req chứa nội dung để khởi tạo thông báo
     * @return đối tượng chứa thông tin của thông báo vừa gửi thành công
     * @throws ParseException đánh dấu lỗi cho sự kiện lấy tên người dùng từ token
     */
    @Override
    public NotificationResponse send(NotificationSendRequest req) throws ParseException {

        // Tạo thông báo
        Notification notification = Notification.builder()
                .title(req.getTitle())
                .message(req.getMessage())
                .build();
        String usernameLogin = getUserNameLogin();
        notification.setCreatedBy(usernameLogin);
        notification = notificationRepository.save(notification);

        // Lưu thông báo đến danh sách người dùng chỉ định
        for (String username : req.getToUsernames()) {
            UserAccount userAccount = userAccountService.getUserByUsername(username);
            UserNotification userNotification = UserNotification.builder()
                    .userAccount(userAccount)
                    .notification(notification)
                    .statusRead(StatusRead.UNREAD)
                    .build();
            userNotification = userNotificationRepository.save(userNotification);

            // Gọi hàm gửi nội dung thông báo realtime tới người dùng bằng SSE
            pushNotificationToUser(userAccount.getUsername(), new UserNotificationResponse(userNotification));
        }
        return modelMapper.map(notification, NotificationResponse.class);
    }

    /**
     * Tạo liên kết đến Client
     *
     * @param userId Id người dùng
     * @return trả ra đối tượng SseEmitter
     */
    @Override
    public SseEmitter createConnection(int userId) {
        UserAccount userAccount = userAccountService.getUserById(userId);
        String username = userAccount.getUsername();
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitters.put(username, emitter);
        emitter.onCompletion(() -> sseEmitters.remove(username));
        emitter.onTimeout(() -> sseEmitters.remove(username));
        return emitter;
    }

    /**
     * Lấy tất cả các thông báo của người dùng đang đăng nhập
     *
     * @param req thông tin phân trang
     * @return danh sách thông báo theo phân trang mong muốn
     * @throws ParseException đánh dấu lỗi cho sự kiện lấy tên người dùng từ token
     */
    @Override
    public PagingResponse<UserNotificationResponse> getAllUserNotificationPaging(PageInfo req) throws ParseException {
        String usernameLogin = getUserNameLogin();
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        Page<UserNotificationResponse> allNotification = userNotificationRepository.findAllByUserAccountId(usernameLogin, pageable);
        return new PagingResponse<>(allNotification);
    }

    /**
     * Đánh dấu đã đọc cho thông báo chỉ định
     *
     * @param req chứa id thông báo đã chỉ định
     * @return đối tượng chứa trạng thái thành công
     */
    @Override
    public UserNotificationMarkReadResponse markReadNotification(UserNotificationMarkReadRequest req) {
        UserNotification userNotification = getById(req.getId());
        if (userNotification.getStatusRead() == StatusRead.UNREAD) {
            throw new AppException(ERROR_NOTIFICATION_ALREADY_READ);
        }
        userNotification.setStatusRead(StatusRead.READ);
        userNotificationRepository.save(userNotification);
        return UserNotificationMarkReadResponse.of(Boolean.TRUE);
    }

    /**
     * Hàm đánh dấu đã đọc cho tất cả các thông báo của người dùng
     *
     * @throws ParseException đánh dấu lỗi cho sự kiện lấy tên người dùng từ token
     */
    @Override
    public UserNotificationMarkReadResponse markReadAllNotification() throws ParseException {
        String usernameLogin = getUserNameLogin();
        List<UserNotification> unreadList = userNotificationRepository.findAllUnread(usernameLogin);
        unreadList.forEach(userNotification -> userNotification.setStatusRead(StatusRead.READ));
        userNotificationRepository.saveAll(unreadList);
        return UserNotificationMarkReadResponse.of(Boolean.TRUE);
    }

    /**
     * Lấy số lượng thông báo chưa đọc
     */
    @Override
    public UnReadAmountResponse countUserNotificationUnread() throws ParseException {
        Long unreadAmount = userNotificationRepository.countUnread(getUserNameLogin());
        return UnReadAmountResponse.of(unreadAmount);
    }

    /**
     * Gửi thông báo realtime đến một người dùng cụ thể qua SSE.
     *
     * @param username     tên đăng nhập của người dùng nhận thông báo.
     * @param notification đối tượng NotificationResponse chứa thông tin thông báo cần gửi.
     */
    private void pushNotificationToUser(String username, UserNotificationResponse notification) {
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

    /**
     * Lấy thông báo người dùng theo ID.
     */
    private UserNotification getById(Integer id) {
        return userNotificationRepository.findById(id).orElseThrow(() -> new AppException(ERROR_NOTIFICATION_NOT_FOUND));
    }

    private String getUserNameLogin() throws ParseException {
        return tokenService.getClaim(tokenService.getJWT(), SUB);
    }
}
