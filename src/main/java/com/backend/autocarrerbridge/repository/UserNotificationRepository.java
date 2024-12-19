package com.backend.autocarrerbridge.repository;

import java.util.List;

import com.backend.autocarrerbridge.dto.response.notification.UserNotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.autocarrerbridge.entity.Notification;
import com.backend.autocarrerbridge.entity.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    @Query("SELECT un.notification " +
            "FROM UserNotification un " +
            "WHERE un.userAccount.id = :userAccountId " +
            "AND un.status <> 0")
    List<Notification> getAllNotificationByUserAccountId(Integer userAccountId);

    /**
     * Lấy danh sách tất cả các user_notification chưa đọc
     */
    @Query("SELECT un " +
            "FROM UserNotification un " +
            "WHERE un.userAccount.username = :username " +
            "AND un.statusRead = 0")
    List<UserNotification> findAllUnread(String username);

    @Query("SELECT count(un.id) " +
            "FROM UserNotification un " +
            "WHERE un.userAccount.username = :username " +
            "AND un.statusRead = 0")
    Long countUnread(String username);

    /**
     * Tìm tất cả thông báo của người dùng cụ thể
     */
    @Query("SELECT new com.backend.autocarrerbridge.dto.response.notification.UserNotificationResponse(un) " +
            "FROM UserNotification  un " +
            "WHERE un.userAccount.username = :username " +
            "ORDER BY un.createdAt desc ")
    Page<UserNotificationResponse> findAllByUserAccountId(String username, Pageable pageable);
}
