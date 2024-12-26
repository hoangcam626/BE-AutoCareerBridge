package com.backend.autocarrerbridge.controller;

import java.text.ParseException;

import com.backend.autocarrerbridge.dto.response.notification.UnReadAmountResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.request.notification.UserNotificationMarkReadRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationMarkReadResponse;
import com.backend.autocarrerbridge.dto.response.notification.UserNotificationResponse;
import com.backend.autocarrerbridge.dto.response.paging.PagingResponse;
import com.backend.autocarrerbridge.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    /**
     * Tạo kết nối SSE để nhận thông báo realtime cho người dùng.
     *
     * @param userId ID của người dùng cần kết nối SSE.
     * @return đối tượng SseEmitter để duy trì kết nối với client.
     */
    @GetMapping("/stream/{userId}")
    public SseEmitter stream(@PathVariable("userId") int userId) {
        return notificationService.createConnection(userId);
    }

    /**
     * Đánh dấu trạng thái đã đọc cho một thông báo cụ thể.
     *
     * @param req đối tượng chứa ID thông báo cần đánh dấu.
     * @return trạng thái thành công việc đánh dấu thông báo đã đọc.
     */
    @PostMapping("/mark-read")
    public ApiResponse<UserNotificationMarkReadResponse> markRead(@Valid @RequestBody UserNotificationMarkReadRequest req){
        var res = notificationService.markReadNotification(req);
        return new ApiResponse<>(res);
    }

    /**
     * Đánh dấu đã đọc cho tất cả thông báo của người dùng.
     *
     * @return trạng thái thành công.
     * @throws ParseException đánh dấu lỗi của việc lấy tên người dùng từ token
     */
    @PostMapping("/mark-read-all")
    public ApiResponse<UserNotificationMarkReadResponse> markRealAll() throws ParseException {
        var res = notificationService.markReadAllNotification();
        return new ApiResponse<>(res);
    }

    /**
     * Lấy danh sách thông báo của người dùng với hỗ trợ phân trang.
     *
     * @param pageNo   số trang cần lấy, mặc định là 0.
     * @param pageSize số lượng phần tử trên mỗi trang, mặc định là 10.
     * @return danh sách thông báo của người dùng và thông tin phân trang.
     * @throws ParseException đánh dấu lỗi của việc lấy tên người dùng từ token
     */
    @GetMapping("/get-all-paging")
    public ApiResponse<PagingResponse<UserNotificationResponse>> getAllUserNotification(@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) throws ParseException {
        var res = notificationService.getAllUserNotificationPaging(PageInfo.builder().pageNo(pageNo).pageSize(pageSize).build());
        return new ApiResponse<>(res);
    }

    /**
     * Lấy số lượng thông báo chưa đọc của người dùng
     * @return số lượng thông báo chưa đọc
     * @throws ParseException đánh dấu lỗi của việc lấy tên người dùng từ token
     */
    @GetMapping("/count-unread")
    public ApiResponse<UnReadAmountResponse> countUnread() throws ParseException {
        var res = notificationService.countUserNotificationUnread();
        return new ApiResponse<>(res);
    }
}
