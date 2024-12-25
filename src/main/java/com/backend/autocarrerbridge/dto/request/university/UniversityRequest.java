package com.backend.autocarrerbridge.dto.request.university;

import com.backend.autocarrerbridge.entity.Location;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityRequest {

  Integer id;
  MultipartFile logoImageId;
  String name;
  String website;
  Integer foundedYear;
  String email;
  String phone;
  String description;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String createdBy;
  String updatedBy;
  Integer userAccountId;
  Integer provinceId;
  Integer districtId;
  Integer wardId;
}
