package com.backend.autocarrerbridge.converter;

import static com.backend.autocarrerbridge.util.Constant.SUB;

import java.text.ParseException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.autocarrerbridge.dto.request.university.UniversityRequest;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
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

    public static University convertToEntity(UniversityRequest universityRequest) {

        University university = University.builder()
                .id(universityRequest.getId())
                .name(universityRequest.getName())
                .website(universityRequest.getWebsite())
                .foundedYear(universityRequest.getFoundedYear())
                .email(universityRequest.getEmail())
                .phone(universityRequest.getPhone())
                .description(universityRequest.getDescription())
                .build();
        if (universityRequest.getLogoImageId() != null
                && !universityRequest.getLogoImageId().isEmpty()) {
            university.setLogoImageId(imageService.uploadFile(universityRequest.getLogoImageId()));
        } else {
            // Xử lý nếu không có logoImage (có thể là gán null hoặc bỏ qua)
            university.setLogoImageId(null);
        }

        university.setCreatedAt(
                universityRequest.getCreatedAt() != null ? universityRequest.getCreatedAt() : LocalDateTime.now());
        university.setUpdatedAt(LocalDateTime.now());

        try {
            String currentUser = tokenService.getClaim(tokenService.getJWT(), SUB);
            university.setCreatedBy(currentUser);
            university.setUpdatedBy(currentUser);
        } catch (ParseException e) {
            throw new AppException(ErrorCode.ERROR_TOKEN_INVALID);
        }

        if (universityRequest.getUserAccountId() != null) {
            UserAccount userAccount = new UserAccount();
            userAccount.setId(universityRequest.getUserAccountId());
            university.setUserAccount(userAccount);
        }
        return university;
    }

    public static UniversityResponse convertToResponse(University university) {

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
