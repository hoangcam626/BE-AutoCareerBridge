package com.backend.autocarrerbridge.converter;

import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;


@Component
public class UniversityConverter {

  private static LocationMapper locationMapper;
  @Autowired
  public UniversityConverter(LocationMapper locationMapper) {
    UniversityConverter.locationMapper = locationMapper; // Khởi tạo LocationMapper
  }

  public static UniversityResponse convertToResponse(University university) {

    if (university.getUserAccount() != null) {
      UserAccount userAccount = new UserAccount();
      userAccount.setId(university.getUserAccount().getId());
      university.setUserAccount(userAccount);
    }
    LocationResponse locationResponse = null;
    if (university.getLocation() != null) {
      locationResponse= locationMapper.toLocationResponse(university.getLocation());
//      locationResponse = locationMapper.toLocationResponse(university.getLocation());
    }

    return UniversityResponse.builder()
        .id(university.getId())
        .name(university.getName())
        .website(university.getWebsite())
        .logoImageId(university.getLogoImageId())
        .foundedYear(university.getFoundedYear())
        .email(university.getEmail())
        .phone(university.getPhone())
        .description(university.getDescription())
        .location(locationResponse)
        .createdAt(university.getCreatedAt())
        .updatedAt(university.getUpdatedAt())
        .createdBy(university.getCreatedBy())
        .updatedBy(university.getUpdatedBy())
        .userAccountId(university.getUserAccount().getId())
        .build();
  }
}
