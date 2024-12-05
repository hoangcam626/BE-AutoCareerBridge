package com.backend.autocarrerbridge.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.impl.TokenServiceImpl;

@Component
public class UniversityConverter {

    private static TokenServiceImpl tokenService;
    private static ImageService imageService;

    private UniversityConverter() {}

    // Constructor được sử dụng để inject TokenServiceImpl vào trong SectionConverter
    @Autowired
    public UniversityConverter(TokenServiceImpl tokenService, ImageService imageService) {
        UniversityConverter.tokenService = tokenService;
        UniversityConverter.imageService = imageService;
    }

    public static UniversityResponse convertToResponse(University university) {

        if (university.getUserAccount() != null) {
            UserAccount userAccount = new UserAccount();
            userAccount.setId(university.getUserAccount().getId());
            university.setUserAccount(userAccount);
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
                .createdAt(university.getCreatedAt())
                .updatedAt(university.getUpdatedAt())
                .createdBy(university.getCreatedBy())
                .updatedBy(university.getUpdatedBy())
                .userAccountId(university.getUserAccount().getId())
                .build();
    }
}
