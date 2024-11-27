package com.backend.autocarrerbridge.dto.request.major;

import static com.backend.autocarrerbridge.util.Constant.CODE_MAJOR_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ID_MAJOR_NOT_NULL_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_MAJOR_NOT_LOGGER_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_SECTION_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NUMBER_MAX_STUDENT_MAJOR_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NUMBER_MIN_STUDENT_MAJOR_MESSAGE;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.backend.autocarrerbridge.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MajorRequest {

    Integer id;

    @NotBlank(message = CODE_MAJOR_NOT_BLANK_MESSAGE)
    @Size(max = 20, message = NAME_MAJOR_NOT_LOGGER_MESSAGE)
    String code;

    @NotBlank(message = NAME_SECTION_NOT_BLANK_MESSAGE)
    @Size(max = 100, message = NAME_MAJOR_NOT_LOGGER_MESSAGE)
    String name;

    Status status;

    @Min(value = 50, message = NUMBER_MIN_STUDENT_MAJOR_MESSAGE)
    @Max(value = 1000, message = NUMBER_MAX_STUDENT_MAJOR_MESSAGE)
    Integer numberStudent;

    @Size(max = 255, message = DESCRIPTION_MESSAGE)
    String description;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;

    @NotNull(message = ID_MAJOR_NOT_NULL_MESSAGE)
    Integer sectionId;
}
