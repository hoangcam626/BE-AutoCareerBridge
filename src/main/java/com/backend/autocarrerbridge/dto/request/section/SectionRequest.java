package com.backend.autocarrerbridge.dto.request.section;


import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_SECTION_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_SECTION_NOT_LOGGER_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.UNIVERSITY_SECTION_NOT_NULL_MESSAGE;

import com.backend.autocarrerbridge.dto.request.major.MajorRequest;
import com.backend.autocarrerbridge.util.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
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
public class SectionRequest {

  Integer id;
  @NotBlank(message = NAME_SECTION_NOT_BLANK_MESSAGE)
  @Size(max = 100, message = NAME_SECTION_NOT_LOGGER_MESSAGE)
  String name;
  @Size(max = 255, message = DESCRIPTION_MESSAGE )
  String description;
  Status status;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String createdBy;
  String updatedBy;
  @NotNull(message = UNIVERSITY_SECTION_NOT_NULL_MESSAGE)
  Integer universityId;
  List<MajorRequest> majorList;
}
