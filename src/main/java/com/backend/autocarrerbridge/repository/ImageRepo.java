package com.backend.autocarrerbridge.repository;

import com.backend.autocarrerbridge.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {
}
