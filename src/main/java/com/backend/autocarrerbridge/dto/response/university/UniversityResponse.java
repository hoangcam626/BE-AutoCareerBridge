package com.backend.autocarrerbridge.dto.response.university;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import java.time.LocalDateTime;

import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversityResponse {

  Integer id;
  Status status;
  String description;
  String email;
  Integer foundedYear;
  Integer logoImageId;
  String name;
  String phone;
  String website;
  LocationResponse location;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String createdBy;
  String updatedBy;
  Integer userAccountId;
}
