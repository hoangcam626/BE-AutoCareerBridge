package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.UserNotification;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Integer> {}
