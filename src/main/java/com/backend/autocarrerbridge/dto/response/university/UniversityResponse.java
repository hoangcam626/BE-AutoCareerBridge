package com.backend.autocarrerbridge.dto.response.university;

import java.time.LocalDateTime;

import com.backend.autocarrerbridge.dto.response.account.UserAccountResponse;
import com.backend.autocarrerbridge.util.enums.State;
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

    private Integer id;
    private Status status;
    private String description;
    private String email;
    private Integer foundedYear;
    private Integer logoImageId;
    private String name;
    private String phone;
    private String website;
    private Integer locationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Integer userAccountId;
    private UserAccountResponse userAccount;
}
