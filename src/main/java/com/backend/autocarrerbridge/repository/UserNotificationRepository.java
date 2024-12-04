package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.UserNotification;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
    @Query("select un.notification from UserNotification un where un.userAccount.id = :userAccountId and un.status <> 0")
    List<Notification> getAllNotificationByUserAccountId(Integer userAccountId);
}
