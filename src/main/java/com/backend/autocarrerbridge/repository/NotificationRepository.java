package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.autocarrerbridge.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {}
