package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {
}
