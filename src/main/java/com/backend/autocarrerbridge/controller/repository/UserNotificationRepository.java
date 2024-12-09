package com.backend.autocarrerbridge.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.autocarrerbridge.entity.Notification;
import com.backend.autocarrerbridge.entity.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    @Query(
            "select un.notification from UserNotification un where un.userAccount.id = :userAccountId and un.status <> 0")
    List<Notification> getAllNotificationByUserAccountId(Integer userAccountId);
}
