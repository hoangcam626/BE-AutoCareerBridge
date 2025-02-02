package com.backend.autocarrerbridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.autocarrerbridge.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {}
