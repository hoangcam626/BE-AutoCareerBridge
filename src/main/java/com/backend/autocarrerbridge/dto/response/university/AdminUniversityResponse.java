package com.backend.autocarrerbridge.dto.response.university;

import com.backend.autocarrerbridge.dto.response.account.UserAccountResponse;
import com.backend.autocarrerbridge.dto.response.location.LocationResponse;
import com.backend.autocarrerbridge.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AdminUniversityResponse {
    private Integer id;
    private Status status;
    private String description;
    private String email;
    private Integer foundedYear;
    private Integer logoImageId;
    private String name;
    private String phone;
    private String website;
    private LocationResponse location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private UserAccountResponse userAccount;
}
