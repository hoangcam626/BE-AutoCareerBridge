package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
