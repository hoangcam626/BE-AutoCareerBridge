package com.backend.autocarrerbridge.dto.request.university;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

@Data
@AllArgsConstructor(staticName = "of")
public class UniversityApprovedRequest {
    @NotBlank(message = NO_CONTENT_MESSAGE)
    private Integer id;
}
